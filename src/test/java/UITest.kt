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
import com.google.gson.Gson
import net.cleverdesk.cleverdesk.ui.UI
import net.cleverdesk.cleverdesk.ui.form.InputField
import org.junit.Test

/**
 * Created by bootslock on 26.04.16.
 */
class UITest {
    @Test
    public fun testIt() {
        val ui = UI()
        val vorname = InputField()
        vorname.placeholder = "Vorname eingeben"
        vorname.max = 20
        vorname.input_name = "vorname"
        ui.addComponent(vorname)

        System.out.println(Gson().toJson(ui))
    }
}