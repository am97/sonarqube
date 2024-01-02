/*
 * SonarQube
 * Copyright (C) 2009-2024 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.issue.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.sonar.api.resources.Languages;
import org.sonar.api.utils.Duration;
import org.sonar.api.utils.Durations;
import org.sonar.db.DbTester;
import org.sonar.db.component.BranchDto;
import org.sonar.db.component.BranchType;
import org.sonar.db.component.ComponentDto;
import org.sonar.db.issue.IssueChangeDto;
import org.sonar.db.issue.IssueDto;
import org.sonar.db.project.ProjectDto;
import org.sonar.db.rule.RuleDto;
import org.sonar.db.user.UserDto;
import org.sonar.server.issue.TextRangeResponseFormatter;
import org.sonar.server.issue.workflow.Transition;
import org.sonarqube.ws.Common;
import org.sonarqube.ws.Issues.Issue;
import org.sonarqube.ws.Issues.Operation;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonar.api.resources.Qualifiers.UNIT_TEST_FILE;
import static org.sonar.api.rule.RuleKey.EXTERNAL_RULE_REPO_PREFIX;
import static org.sonar.api.rules.RuleType.CODE_SMELL;
import static org.sonar.api.rules.RuleType.SECURITY_HOTSPOT;
import static org.sonar.api.utils.DateUtils.formatDateTime;
import static org.sonar.db.component.ComponentTesting.newPrivateProjectDto;
import static org.sonar.db.issue.IssueTesting.newIssue;
import static org.sonar.db.issue.IssueTesting.newIssuechangeDto;
import static org.sonar.db.rule.RuleTesting.newRule;
import static org.sonar.db.user.UserTesting.newUserDto;
import static org.sonar.server.issue.index.IssueScope.MAIN;
import static org.sonar.server.issue.index.IssueScope.TEST;

@RunWith(MockitoJUnitRunner.class)
public class SearchResponseFormatFormatOperationTest {
  @Rule
  public DbTester db = DbTester.create();
  private final Durations durations = new Durations();
  private final Languages languages = mock(Languages.class);
  private final TextRangeResponseFormatter textRangeResponseFormatter = mock(TextRangeResponseFormatter.class);
  private final UserResponseFormatter userResponseFormatter = mock(UserResponseFormatter.class);
  private final Common.User user = mock(Common.User.class);
  private final SearchResponseFormat searchResponseFormat = new SearchResponseFormat(durations, languages, textRangeResponseFormatter, userResponseFormatter);

  private SearchResponseData searchResponseData;
  private IssueDto issueDto;
  private ComponentDto componentDto;
  private UserDto userDto;

  @Before
  public void setUp() {
    searchResponseData = newSearchResponseDataMainBranch();
  }

  @Test
  public void formatOperation_should_add_components_to_response() {
    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getComponentsList()).hasSize(1);
    assertThat(result.getComponentsList().get(0).getKey()).isEqualTo(issueDto.getComponentKey());
  }

  @Test
  public void formatOperation_should_add_rules_to_response() {
    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getRulesList()).hasSize(1);
    assertThat(result.getRulesList().get(0).getKey()).isEqualTo(issueDto.getRuleKey().toString());
  }

  @Test
  public void formatOperation_should_add_users_to_response() {
    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getUsersList()).hasSize(1);
    assertThat(result.getUsers(0)).isSameAs(user);
  }

  @Test
  public void formatOperation_should_add_issue_to_response() {
    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertIssueEqualsIssueDto(result.getIssue(), issueDto);
  }

  private void assertIssueEqualsIssueDto(Issue issue, IssueDto issueDto) {
    assertThat(issue.getKey()).isEqualTo(issueDto.getKey());
    assertThat(issue.getType().getNumber()).isEqualTo(issueDto.getType());
    assertThat(issue.getComponent()).isEqualTo(issueDto.getComponentKey());
    assertThat(issue.getRule()).isEqualTo(issueDto.getRuleKey().toString());
    assertThat(issue.getSeverity()).hasToString(issueDto.getSeverity());
    assertThat(issue.getAssignee()).isEqualTo(userDto.getLogin());
    assertThat(issue.getResolution()).isEqualTo(issueDto.getResolution());
    assertThat(issue.getStatus()).isEqualTo(issueDto.getStatus());
    assertThat(issue.getMessage()).isEqualTo(issueDto.getMessage());
    assertThat(new ArrayList<>(issue.getTagsList())).containsExactlyInAnyOrderElementsOf(issueDto.getTags());
    assertThat(issue.getLine()).isEqualTo(issueDto.getLine());
    assertThat(issue.getHash()).isEqualTo(issueDto.getChecksum());
    assertThat(issue.getAuthor()).isEqualTo(issueDto.getAuthorLogin());
    assertThat(issue.getCreationDate()).isEqualTo(formatDateTime(issueDto.getIssueCreationDate()));
    assertThat(issue.getUpdateDate()).isEqualTo(formatDateTime(issueDto.getIssueUpdateDate()));
    assertThat(issue.getCloseDate()).isEqualTo(formatDateTime(issueDto.getIssueCloseDate()));
    assertThat(issue.getQuickFixAvailable()).isEqualTo(issueDto.isQuickFixAvailable());
    assertThat(issue.getRuleDescriptionContextKey()).isEqualTo(issueDto.getOptionalRuleDescriptionContextKey().orElse(null));
  }

  @Test
  public void formatOperation_should_not_add_issue_when_several_issue() {
    searchResponseData = new SearchResponseData(List.of(createIssue(), createIssue()));

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue()).isEqualTo(Issue.getDefaultInstance());
  }

  private static IssueDto createIssue() {
    RuleDto ruleDto = newRule();
    String projectUuid = "project_uuid_" + randomAlphanumeric(5);
    ComponentDto projectDto = newPrivateProjectDto();
    projectDto.setBranchUuid(projectUuid);
    return newIssue(ruleDto, projectUuid, "project_key_" + randomAlphanumeric(5), projectDto);
  }

  @Test
  public void formatOperation_should_add_branch_on_issue() {
    String branchName = randomAlphanumeric(5);
    searchResponseData = newSearchResponseDataBranch(branchName);
    Operation result = searchResponseFormat.formatOperation(searchResponseData);
    assertThat(result.getIssue().getBranch()).isEqualTo(branchName);
  }

  @Test
  public void formatOperation_should_add_pullrequest_on_issue() {
    searchResponseData = newSearchResponseDataPr("pr1");
    Operation result = searchResponseFormat.formatOperation(searchResponseData);
    assertThat(result.getIssue().getPullRequest()).isEqualTo("pr1");
  }

  @Test
  public void formatOperation_should_add_project_on_issue() {
    issueDto.setProjectUuid(componentDto.uuid());

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getProject()).isEqualTo(componentDto.getKey());
  }

  @Test
  public void formatOperation_should_add_external_rule_engine_on_issue() {
    issueDto.setExternal(true);
    String expected = randomAlphanumeric(5);
    issueDto.setRuleKey(EXTERNAL_RULE_REPO_PREFIX + expected, randomAlphanumeric(5));

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getExternalRuleEngine()).isEqualTo(expected);
  }

  @Test
  public void formatOperation_should_add_effort_and_debt_on_issue() {
    long effort = 60L;
    issueDto.setEffort(effort);
    String expected = durations.encode(Duration.create(effort));

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getEffort()).isEqualTo(expected);
    assertThat(result.getIssue().getDebt()).isEqualTo(expected);
  }

  @Test
  public void formatOperation_should_add_scope_test_on_issue_when_unit_test_file() {
    componentDto.setQualifier(UNIT_TEST_FILE);

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getScope()).isEqualTo(TEST.name());
  }

  @Test
  public void formatOperation_should_add_scope_main_on_issue_when_not_unit_test_file() {
    componentDto.setQualifier(randomAlphanumeric(5));

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getScope()).isEqualTo(MAIN.name());
  }

  @Test
  public void formatOperation_should_add_actions_on_issues() {
    Set<String> expectedActions = Set.of("actionA", "actionB");
    searchResponseData.addActions(issueDto.getKey(), expectedActions);

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getActions().getActionsList()).containsExactlyInAnyOrderElementsOf(expectedActions);
  }

  @Test
  public void formatOperation_should_add_transitions_on_issues() {
    Set<String> expectedTransitions = Set.of("transitionone", "transitiontwo");
    searchResponseData.addTransitions(issueDto.getKey(), createFakeTransitions(expectedTransitions));

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getTransitions().getTransitionsList()).containsExactlyInAnyOrderElementsOf(expectedTransitions);
  }

  private static List<Transition> createFakeTransitions(Collection<String> transitions) {
    return transitions.stream()
      .map(transition -> Transition.builder(transition).from("OPEN").to("RESOLVED").build())
      .collect(toList());
  }

  @Test
  public void formatOperation_should_add_comments_on_issues() {
    IssueChangeDto issueChangeDto = newIssuechangeDto(issueDto);
    searchResponseData.setComments(List.of(issueChangeDto));

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().getComments().getCommentsList()).hasSize(1).extracting(Common.Comment::getKey).containsExactly(issueChangeDto.getKey());
  }

  @Test
  public void formatOperation_should_not_set_severity_for_security_hotspot_issue() {
    issueDto.setType(SECURITY_HOTSPOT);

    Operation result = searchResponseFormat.formatOperation(searchResponseData);

    assertThat(result.getIssue().hasSeverity()).isFalse();
  }

  private SearchResponseData newSearchResponseDataMainBranch() {
    ComponentDto projectDto = db.components().insertPublicProject();
    BranchDto branchDto = db.getDbClient().branchDao().selectByUuid(db.getSession(), projectDto.uuid()).get();
    return newSearchResponseData(projectDto, branchDto);
  }

  private SearchResponseData newSearchResponseDataBranch(String name) {
    ProjectDto projectDto = db.components().insertPublicProjectDto();
    BranchDto branch = db.components().insertProjectBranch(projectDto, b -> b.setKey(name));
    ComponentDto branchComponent = db.components().getComponentDto(branch);
    return newSearchResponseData(branchComponent, branch);
  }

  private SearchResponseData newSearchResponseDataPr(String name) {
    ProjectDto projectDto = db.components().insertPublicProjectDto();
    BranchDto branch = db.components().insertProjectBranch(projectDto, b -> b.setKey(name).setBranchType(BranchType.PULL_REQUEST));
    ComponentDto branchComponent = db.components().getComponentDto(branch);
    return newSearchResponseData(branchComponent, branch);
  }

  private SearchResponseData newSearchResponseData(ComponentDto component, BranchDto branch) {
    RuleDto ruleDto = newRule();
    userDto = newUserDto();
    componentDto = component;
    issueDto = newIssue(ruleDto, component.branchUuid(), component.getKey(), component)
      .setType(CODE_SMELL)
      .setRuleDescriptionContextKey("context_key_" + randomAlphanumeric(5))
      .setAssigneeUuid(userDto.getUuid())
      .setResolution("resolution_" + randomAlphanumeric(5))
      .setIssueCreationDate(new Date(currentTimeMillis() - 2_000))
      .setIssueUpdateDate(new Date(currentTimeMillis() - 1_000))
      .setIssueCloseDate(new Date(currentTimeMillis()));

    SearchResponseData searchResponseData = new SearchResponseData(issueDto);
    searchResponseData.addComponents(List.of(component));
    searchResponseData.addRules(List.of(ruleDto));
    searchResponseData.addUsers(List.of(userDto));
    searchResponseData.addBranches(List.of(branch));

    when(userResponseFormatter.formatUser(any(Common.User.Builder.class), eq(userDto))).thenReturn(user);

    return searchResponseData;
  }
}
