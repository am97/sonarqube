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

export default function ExtendQualityProfileIcon({
  ...iconProps
}: Omit<IconProps, 'viewBox' | 'fill'>) {
  return (
    <Icon {...iconProps} viewBox="0 0 82 64">
      <path
        d="M72.576 35.0882L67.4217 29.9332C66.5881 29.1046 65.4624 28.6389 64.2952 28.6389L51.7668 28.6389C49.3254 28.6389 47.3467 30.6176 47.3467 33.0591L47.3471 59.5799C47.3471 62.0207 49.3258 64.0001 51.7673 64.0001H69.4474C71.8785 64.0001 73.8675 62.011 73.8675 59.5799V38.2113C73.8675 37.0441 73.4048 35.9183 72.576 35.0882ZM70.5524 59.5799C70.5524 60.1902 70.0577 60.685 69.4474 60.685H51.7682C51.1578 60.685 50.6632 60.1902 50.6632 59.5799L50.6618 33.068C50.6618 32.4578 51.1566 31.963 51.7668 31.963H62.8172V37.4792C62.8172 38.6996 63.8069 39.6893 65.0273 39.6893H70.4903V59.5799H70.5524ZM53.9769 47.9771C53.9769 48.8956 54.7228 49.6346 55.6344 49.6346H60.6071H65.5798C66.4983 49.6346 67.2373 48.8956 67.2373 47.9771C67.2373 47.0585 66.4983 46.3195 65.5798 46.3195H55.6344C54.7228 46.3195 53.9769 47.0654 53.9769 47.9771ZM65.5798 52.9497H55.6344C54.7228 52.9497 53.9769 53.6956 53.9769 54.6073C53.9769 55.5189 54.7193 56.2648 55.6344 56.2648H65.5798C66.4949 56.2648 67.2373 55.5224 67.2373 54.6073C67.2373 53.6922 66.4983 52.9497 65.5798 52.9497Z"
        fill="#236A97"
      />
      <path
        d="M33.9916 6.44927L28.8373 1.29428C28.0036 0.465641 26.8779 6.90647e-06 25.7107 6.90647e-06L13.1824 0C10.7409 0 8.76221 1.97871 8.76221 4.42014L8.76266 30.941C8.76266 33.3818 10.7414 35.3612 13.1828 35.3612H30.8629C33.294 35.3612 35.2831 33.3721 35.2831 30.941V9.57238C35.2831 8.40519 34.8203 7.27943 33.9916 6.44927ZM31.968 30.941C31.968 31.5513 31.4732 32.0461 30.8629 32.0461H13.1837C12.5733 32.0461 12.0787 31.5513 12.0787 30.941L12.0773 4.42913C12.0773 3.81887 12.5721 3.32409 13.1824 3.32409H24.2327V8.8403C24.2327 10.0607 25.2224 11.0504 26.4428 11.0504H31.9058V30.941H31.968ZM15.3924 19.3381C15.3924 20.2567 16.1383 20.9957 17.05 20.9957H22.0226H26.9953C27.9139 20.9957 28.6529 20.2567 28.6529 19.3381C28.6529 18.4196 27.9139 17.6806 26.9953 17.6806H17.05C16.1383 17.6806 15.3924 18.4265 15.3924 19.3381ZM26.9953 24.3108H17.05C16.1383 24.3108 15.3924 25.0567 15.3924 25.9684C15.3924 26.88 16.1349 27.6259 17.05 27.6259H26.9953C27.9104 27.6259 28.6529 26.8835 28.6529 25.9684C28.6529 25.0532 27.9139 24.3108 26.9953 24.3108Z"
        fill="#666666"
      />
      <path
        d="M23.3694 40.4087C23.3694 39.6032 22.7187 38.9525 21.9132 38.9525C21.1078 38.9525 20.4571 39.6032 20.4571 40.4087L20.4571 44.7773C20.4571 47.1891 22.4138 49.1458 24.8256 49.1458L38.7822 49.1458L35.4421 52.4859C34.8733 53.0548 34.8733 53.9785 35.4421 54.5473C36.0109 55.1162 36.9347 55.1162 37.5035 54.5473L43.3283 48.7226C43.8971 48.1538 43.8971 47.23 43.3283 46.6612L37.5035 40.8365C36.9347 40.2676 36.0109 40.2676 35.4421 40.8365C34.8733 41.4053 34.8733 42.329 35.4421 42.8979L38.7822 46.2334L24.8256 46.2334C24.0202 46.2334 23.3694 45.5827 23.3694 44.7773L23.3694 40.4087Z"
        fill="#666666"
      />
    </Icon>
  );
}
