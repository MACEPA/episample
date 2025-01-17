/*
 * Copyright (C) 2013 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.path.common.android.provider;

import java.util.List;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * ODK Survey (only)
 *
 * Tracks what forms are available in the ODK Survey forms directory.
 */
public final class FormsColumns implements BaseColumns {
  // This class cannot be instantiated
  private FormsColumns() {
  }

  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.opendatakit.form";
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.opendatakit.form";

  /** The form_id that holds the common javascript files for Survey */
  public static final String COMMON_BASE_FORM_ID = "framework";

  // These are the only things needed for an insert
  public static final String TABLE_ID = "tableId"; // for Tables linkage
  public static final String FORM_ID = "formId";
  public static final String FORM_VERSION = "formVersion"; // can be null
  public static final String DISPLAY_NAME = "displayName";
  public static final String DESCRIPTION = "description"; // can be null

  /** ODK2: within the media directory */
  public static final String APP_RELATIVE_FORM_FILE_PATH = "appRelativeFormFilePath";
  /** directory containing formDef.json */
  public static final String APP_RELATIVE_FORM_MEDIA_PATH = "appRelativeFormMediaPath";
  /** relative path for WebKit */
  public static final String FORM_PATH = "formPath";

  /** locale that the form should start in */
  public static final String DEFAULT_FORM_LOCALE = "defaultFormLocale";
  /** column name for the 'instance_name' (display name) of a submission */
  public static final String INSTANCE_NAME = "instanceName";

  // these are generated for you (but you can insert something else if you
  // want)
  public static final String DISPLAY_SUBTEXT = "displaySubtext";
  public static final String MD5_HASH = "md5Hash";
  public static final String JSON_MD5_HASH = "jsonMd5Hash";
  public static final String DATE = "date"; // last modification date

  // NOTE: this omits _ID (the primary key)
  public static final String[] formsDataColumnNames = { DISPLAY_NAME, DISPLAY_SUBTEXT, DESCRIPTION,
      TABLE_ID, FORM_ID, FORM_VERSION, APP_RELATIVE_FORM_FILE_PATH, APP_RELATIVE_FORM_MEDIA_PATH, FORM_PATH, MD5_HASH,
      JSON_MD5_HASH, DATE,
      DEFAULT_FORM_LOCALE, INSTANCE_NAME };

  /**
   * Get the create sql for the forms table (ODK Survey only).
   *
   * @return
   */
  public static String getTableCreateSql(String tableName) {
    //@formatter:off
    return "CREATE TABLE IF NOT EXISTS " + tableName + " ("
           + _ID + " integer not null primary key, " // for Google...
           + FORM_ID + " text not null unique, "  // real PK
           + DISPLAY_NAME + " text not null, "
           + DISPLAY_SUBTEXT + " text not null, "
           + DESCRIPTION + " text, "
           + TABLE_ID + " text null, " // null if framework
           + FORM_VERSION + " text, "
           + APP_RELATIVE_FORM_FILE_PATH + " text null, "
           + APP_RELATIVE_FORM_MEDIA_PATH + " text not null, "
           + FORM_PATH + " text not null, "
           + MD5_HASH + " text not null, "
           + JSON_MD5_HASH + " text not null, "
           + DATE + " integer not null, " // milliseconds
           + DEFAULT_FORM_LOCALE + " text null, "
           + INSTANCE_NAME + " text null )";
    //@formatter:on
  }

  public static String extractAppNameFromFormsUri(Uri uri) {
    List<String> segments = uri.getPathSegments();

    if (segments.size() < 1) {
      throw new IllegalArgumentException("Unknown URI (incorrect number of segments!) " + uri);
    }

    String appName = segments.get(0);
    return appName;
  }
}