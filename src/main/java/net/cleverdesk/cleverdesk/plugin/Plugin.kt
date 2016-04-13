/*
 * Cleverdesk, a modular helpdesk
 *
 *   Copyright (C) 2016 Jonas Kuche
 *   Copyright (C) 2016 Jonas Franz
 *   Copyright (C) 2016 Konrad Langenberg
 *
 *   This file is part of Cleverdesk.
 *
 *   Cleverdesk is free software: you can redistribute it and/or modify it under the terms of the
 *   GNU General Public License as published by the Free Software Foundation, either version 2 of the
 *   License, or (at your option) any later version.
 *
 *   Cleverdesk is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *   See the GNU General Public License for more details. You should have received a copy of the GNU
 *   General Public License along with Cleverdesk. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package net.cleverdesk.cleverdesk.plugin

import net.cleverdesk.cleverdesk.launcher.Launcher

abstract class Plugin(launcher: Launcher) {

    public val launcher: Launcher = launcher
    public val listenerManager: Launcher = launcher


    public fun enable() {

    }

    public fun disable() {

    }

}