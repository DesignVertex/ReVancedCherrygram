package li.auna.patches.telegram.messaging.timer

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import li.auna.patches.telegram.messaging.ads.fingerprints.HideSponsoredMessagesFingerprint

@Patch(
    name = "No Media Timer Limit",
    description = "Remove the media timer limit.",
    compatiblePackages = [
        CompatiblePackage("org.telegram.messenger"),
		CompatiblePackage("org.telegram.messenger.web")
    ],
)
@Suppress("unused")
object NoMediaTimerLimit : BytecodePatch(
    setOf(HideSponsoredMessagesFingerprint)
) {
    override fun execute(context: BytecodeContext) {
//        context.findClass("TLRPC$/Message;").let {
//            it?.mutableClass?.fields?.find { field -> field.name == "destroyTime" }?.let { field ->
////                todo.
//            }
//        }
    }
}
