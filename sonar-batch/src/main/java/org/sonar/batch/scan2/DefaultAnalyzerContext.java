/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.batch.scan2;

import org.sonar.api.batch.analyzer.AnalyzerContext;
import org.sonar.api.batch.analyzer.issue.AnalyzerIssue;
import org.sonar.api.batch.analyzer.issue.AnalyzerIssueBuilder;
import org.sonar.api.batch.analyzer.issue.internal.DefaultAnalyzerIssueBuilder;
import org.sonar.api.batch.analyzer.measure.AnalyzerMeasure;
import org.sonar.api.batch.analyzer.measure.AnalyzerMeasureBuilder;
import org.sonar.api.batch.analyzer.measure.internal.DefaultAnalyzerMeasure;
import org.sonar.api.batch.analyzer.measure.internal.DefaultAnalyzerMeasureBuilder;
import org.sonar.api.batch.bootstrap.ProjectDefinition;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.measures.Metric;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.internal.DefaultIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.batch.issue.ModuleIssues;
import org.sonar.core.component.ComponentKeys;
import org.sonar.core.issue.DefaultIssueBuilder;

import java.io.Serializable;

public class DefaultAnalyzerContext implements AnalyzerContext {

  private final AnalyzerMeasureCache measureCache;
  private ProjectDefinition def;
  private ModuleIssues moduleIssues;
  private Settings settings;
  private FileSystem fs;
  private ActiveRules activeRules;

  public DefaultAnalyzerContext(ProjectDefinition def, AnalyzerMeasureCache measureCache,
    ModuleIssues moduleIssues, Settings settings, FileSystem fs, ActiveRules activeRules) {
    this.def = def;
    this.measureCache = measureCache;
    this.moduleIssues = moduleIssues;
    this.settings = settings;
    this.fs = fs;
    this.activeRules = activeRules;
  }

  @Override
  public Settings settings() {
    return settings;
  }

  @Override
  public FileSystem fileSystem() {
    return fs;
  }

  @Override
  public ActiveRules activeRules() {
    return activeRules;
  }

  @Override
  public <G extends Serializable> AnalyzerMeasureBuilder<G> measureBuilder() {
    return new DefaultAnalyzerMeasureBuilder<G>();
  }

  @Override
  public AnalyzerMeasure getMeasure(String metricKey) {
    return measureCache.byMetric(def.getKey(), metricKey);
  }

  @Override
  public <G extends Serializable> AnalyzerMeasure<G> getMeasure(Metric<G> metric) {
    return (AnalyzerMeasure<G>) measureCache.byMetric(def.getKey(), metric.key());
  }

  @Override
  public AnalyzerMeasure getMeasure(InputFile file, String metricKey) {
    return measureCache.byMetric(ComponentKeys.createEffectiveKey(def.getKey(), file), metricKey);
  }

  @Override
  public <G extends Serializable> AnalyzerMeasure<G> getMeasure(InputFile file, Metric<G> metric) {
    return (AnalyzerMeasure<G>) measureCache.byMetric(ComponentKeys.createEffectiveKey(def.getKey(), file), metric.key());
  }

  @Override
  public void addMeasure(AnalyzerMeasure<?> measure) {
    if (measure.inputFile() != null) {
      measureCache.put(ComponentKeys.createEffectiveKey(def.getKey(), measure.inputFile()), (DefaultAnalyzerMeasure) measure);
    } else {
      measureCache.put(def.getKey(), (DefaultAnalyzerMeasure) measure);
    }
  }

  @Override
  public AnalyzerIssueBuilder issueBuilder() {
    return new DefaultAnalyzerIssueBuilder();
  }

  @Override
  public void addIssue(AnalyzerIssue issue) {
    DefaultIssueBuilder builder = new DefaultIssueBuilder()
      .projectKey(def.getKey());
    if (issue.inputFile() != null) {
      builder.componentKey(ComponentKeys.createEffectiveKey(def.getKey(), issue.inputFile()));
    } else {
      builder.componentKey(def.getKey());
    }

    moduleIssues.initAndAddIssue((DefaultIssue) builder
      .ruleKey(RuleKey.of(issue.ruleKey().repository(), issue.ruleKey().rule()))
      .message(issue.message())
      .line(issue.line())
      .effortToFix(issue.effortToFix())
      .build());
  }

}
