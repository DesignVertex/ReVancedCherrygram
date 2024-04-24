package li.auna.patches.telegram.messaging.misc.deletedmessages

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import li.auna.patches.telegram.messaging.misc.deletedmessages.fingerprints.MarkMessagesAsDeletedFingerprint1
import li.auna.patches.telegram.messaging.misc.deletedmessages.fingerprints.MarkMessagesAsDeletedFingerprint2

@Patch(
    name = "View Deleted Messages",
    description = "View deleted messages in chat.",
    compatiblePackages = [
        CompatiblePackage("org.telegram.messenger"),
    ],
)
@Suppress("unused")
object ViewDeletedMessages : BytecodePatch(
    setOf(MarkMessagesAsDeletedFingerprint1, MarkMessagesAsDeletedFingerprint2)
) {
    override fun execute(context: BytecodeContext) {
        MarkMessagesAsDeletedFingerprint1.result?.mutableMethod?.addInstructions(0,
            """
            const/4 v0, 0x0
            return-object v0
            """
        )
            ?: throw MarkMessagesAsDeletedFingerprint1.exception

        MarkMessagesAsDeletedFingerprint2.result?.mutableMethod?.addInstructions(0,
            """
            const/4 v0, 0x0
            return-object v0
            """
        )
            ?: throw MarkMessagesAsDeletedFingerprint2.exception
    }
}
