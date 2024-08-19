package li.auna.patches.telegram.messaging.misc.typingindicator

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import li.auna.patches.telegram.messaging.misc.typingindicator.fingerprints.NeedSendtypingFingerprint

@Patch(
    name = "Hide Typing Indicator",
    description = "Hide the typing indicator for outgoing messages.",
    compatiblePackages = [
        CompatiblePackage("org.telegram.messenger"),
		CompatiblePackage("org.telegram.messenger.web")
    ],
)
@Suppress("unused")
object HideTypingIndicator : BytecodePatch(
    setOf(NeedSendtypingFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        NeedSendtypingFingerprint.result?.mutableMethod?.addInstruction(1, "return-void")
            ?: throw NeedSendtypingFingerprint.exception
    }
}
