/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.cleverdesk.cleverdesk.ui

import net.cleverdesk.cleverdesk.ui.form.Form

/**
 * An [Action] represents an implemented Frontend feature like a [SubmitFormAction] or a [OpenPageAction]
 */
interface Action {
    val name: String
}

/**
 * Submits a given [form].
 * @see [Form]
 */
class SubmitFormAction(form: Form) : Action {
    override val name: String = "OpenPage"
    val form: String = form.identifer
}

/**
 * Open [page]. [page] must be a relativ path like Test/ExamplePage
 */
class OpenPageAction(page: String) : Action {
    override val name: String = "OpenPage"
    val page: String = page
}

/**
 * Opens [url] in [target].
 */
class OpenURLAction(url: String) : Action {
    override val name: String = "OpenURL"
    val url: String = url
    /**
     * Only supported in Web
     */
    var target: BrowserTarget? = null

    enum class BrowserTarget {
        NEW_TAB, ACTUAL_TAB, MODAL;
    }
}

/**
 * Closes the actual page/modal/etc.
 */
class ClosePageAction() : Action {
    override val name: String = "CloseModal"
}

