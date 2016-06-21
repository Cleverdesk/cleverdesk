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
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.database.drivers.MongoDriver
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import org.junit.Test

class UITest {

    @Test
    public fun testIt() {
        val test_launcher: Launcher = Launcher()

        val dbDriver = MongoDriver()
        dbDriver.launcher = test_launcher
        test_launcher.plugins.add(dbDriver)

        val testPL = PluginExample()
        testPL.launcher = test_launcher
        testPL.description = object : PluginDescription {
            override val name: String
                get() = "Test-Plugin"
            override val author: String
                get() = "Cleverdesk"
            override val description: String
                get() = "Only for UI & DB testing!"
        }
        test_launcher.plugins.add(testPL)

        test_launcher.enable()

        Thread.sleep(4000)

        val user = User(test_launcher)
        user.username = "MaxTheMustermann"
        user.first_name = "Max"
        user.last_name = "Mustermann"
        user.external_data = mapOf(Pair("email", "mail@example.com"))
        user.password = "very_secure"
        test_launcher.database?.upload(user)
        print("Uploaded user: ${test_launcher.database != null}")
        Thread.sleep(4000)
    }
}