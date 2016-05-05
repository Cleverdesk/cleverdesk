package net.cleverdesk.cleverdesk.ui.form

/**
 * Created by matthias on 03.05.16.
 */
class Textarea() : AbstractInputField<String>() {
    override val name: String = "Textarea"
    override var input_name: String = ""

    var readOnly: Boolean = false
    var text: String = ""
}