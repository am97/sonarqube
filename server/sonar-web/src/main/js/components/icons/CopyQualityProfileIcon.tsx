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
import * as React from 'react';
import Icon, { IconProps } from './Icon';

export default function CopyQualityProfileIcon({
  ...iconProps
}: Omit<IconProps, 'viewBox' | 'fill'>) {
  return (
    <Icon {...iconProps} viewBox="0 0 82 64">
      <path
        d="M25.2293 20.4493L20.0751 15.2943C19.2414 14.4656 18.1157 14 16.9485 14L4.42014 14C1.97871 14 0 15.9787 0 18.4201L0.000448921 44.941C0.000448921 47.3818 1.97915 49.3612 4.42059 49.3612H22.1007C24.5318 49.3612 26.5209 47.3721 26.5209 44.941V23.5724C26.5209 22.4052 26.0581 21.2794 25.2293 20.4493ZM23.2058 44.941C23.2058 45.5513 22.711 46.0461 22.1007 46.0461H4.42153C3.81113 46.0461 3.31649 45.5513 3.31649 44.941L3.31511 18.4291C3.31511 17.8189 3.80989 17.3241 4.42014 17.3241H15.4705V22.8403C15.4705 24.0607 16.4602 25.0504 17.6806 25.0504H23.1436V44.941H23.2058ZM6.63022 33.3381C6.63022 34.2567 7.37611 34.9957 8.28777 34.9957H13.2604H18.2331C19.1517 34.9957 19.8906 34.2567 19.8906 33.3381C19.8906 32.4196 19.1517 31.6806 18.2331 31.6806H8.28777C7.37611 31.6806 6.63022 32.4265 6.63022 33.3381ZM18.2331 38.3108H8.28777C7.37611 38.3108 6.63022 39.0567 6.63022 39.9684C6.63022 40.88 7.37266 41.6259 8.28777 41.6259H18.2331C19.1482 41.6259 19.8906 40.8835 19.8906 39.9684C19.8906 39.0532 19.1517 38.3108 18.2331 38.3108Z"
        fill="#666666"
      />
      <path
        d="M80.2293 20.4493L75.0751 15.2943C74.2414 14.4656 73.1157 14 71.9485 14L59.4201 14C56.9787 14 55 15.9787 55 18.4201L55.0004 44.941C55.0004 47.3818 56.9792 49.3612 59.4206 49.3612H77.1007C79.5318 49.3612 81.5209 47.3721 81.5209 44.941V23.5724C81.5209 22.4052 81.0581 21.2794 80.2293 20.4493ZM78.2058 44.941C78.2058 45.5513 77.711 46.0461 77.1007 46.0461H59.4215C58.8111 46.0461 58.3165 45.5513 58.3165 44.941L58.3151 18.4291C58.3151 17.8189 58.8099 17.3241 59.4201 17.3241H70.4705V22.8403C70.4705 24.0607 71.4602 25.0504 72.6806 25.0504H78.1436V44.941H78.2058ZM61.6302 33.3381C61.6302 34.2567 62.3761 34.9957 63.2878 34.9957H68.2604H73.2331C74.1517 34.9957 74.8906 34.2567 74.8906 33.3381C74.8906 32.4196 74.1517 31.6806 73.2331 31.6806H63.2878C62.3761 31.6806 61.6302 32.4265 61.6302 33.3381ZM73.2331 38.3108H63.2878C62.3761 38.3108 61.6302 39.0567 61.6302 39.9684C61.6302 40.88 62.3727 41.6259 63.2878 41.6259H73.2331C74.1482 41.6259 74.8906 40.8835 74.8906 39.9684C74.8906 39.0532 74.1517 38.3108 73.2331 38.3108Z"
        fill="#236A97"
      />
      <path
        d="M50.3424 33.4672C50.8937 32.916 50.8937 32.0208 50.3424 31.4695L43.2864 24.4134C42.7351 23.8622 41.8399 23.8622 41.2886 24.4134C40.7374 24.9647 40.7374 25.8599 41.2886 26.4112L45.9412 31.0594H32.4112C31.6306 31.0594 31 31.69 31 32.4706C31 33.2512 31.6306 33.8818 32.4112 33.8818H45.9368L41.293 38.53C40.7418 39.0812 40.7418 39.9765 41.293 40.5277C41.8443 41.079 42.7395 41.079 43.2908 40.5277L50.3468 33.4717L50.3424 33.4672Z"
        fill="#666666"
      />
    </Icon>
  );
}
