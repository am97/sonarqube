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
package org.sonar.core.documentation;

import java.util.Optional;
import javax.annotation.Nullable;
import org.sonar.api.utils.Version;
import org.sonar.core.platform.SonarQubeVersion;

public class DefaultDocumentationLinkGenerator implements DocumentationLinkGenerator {
  private static final String DOCUMENTATION_PUBLIC_URL = "https://docs.sonarqube.org/";

  private final String documentationBaseUrl;

  public DefaultDocumentationLinkGenerator(SonarQubeVersion sonarQubeVersion) {
    Version version = sonarQubeVersion.get();
    this.documentationBaseUrl = DOCUMENTATION_PUBLIC_URL + version.major() + "." + version.minor();
  }

  @Override
  public String getDocumentationLink(@Nullable String suffix) {
    return documentationBaseUrl + Optional.ofNullable(suffix).orElse("");
  }
}
