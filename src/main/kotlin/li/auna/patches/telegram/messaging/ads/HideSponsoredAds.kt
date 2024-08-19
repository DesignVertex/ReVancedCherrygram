package li.auna.patches.telegram.messaging.ads

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import li.auna.patches.telegram.messaging.ads.fingerprints.HideSponsoredMessagesFingerprint

@Patch(
    name = "Hide Sponsored Ads",
    description = "Hide sponsored ads in channels.",
    compatiblePackages = [
        CompatiblePackage("org.telegram.messenger"),
		CompatiblePackage("org.telegram.messenger.web")
    ],
)
@Suppress("unused")
object HideSponsoredAds : BytecodePatch(
    setOf(HideSponsoredMessagesFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        HideSponsoredMessagesFingerprint.result?.mutableMethod?.addInstruction(1, "return-void")
            ?: throw HideSponsoredMessagesFingerprint.exception
    }
}
