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
package org.sonar.server.platform.db.migration.version.v96;

import java.sql.SQLException;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.db.CoreDbTester;

public class CreateIndexForPushEventsTest {

  @Rule
  public final CoreDbTester db = CoreDbTester.createForSchema(CreateIndexForPushEventsTest.class, "schema.sql");

  private final CreateIndexForPushEvents underTest = new CreateIndexForPushEvents(db.database());

  @Test
  public void should_create_index() throws SQLException {
    db.assertIndexDoesNotExist("push_events", "idx_push_even_crea_uuid_proj");
    underTest.execute();
    db.assertIndex("push_events", "idx_push_even_crea_uuid_proj", "created_at", "uuid", "project_uuid");
  }

  @Test
  public void migration_should_be_reentrant() throws SQLException {
    db.assertIndexDoesNotExist("push_events", "idx_push_even_crea_uuid_proj");

    underTest.execute();
    // re-entrant
    underTest.execute();

    db.assertIndex("push_events", "idx_push_even_crea_uuid_proj", "created_at", "uuid", "project_uuid");
  }

}
