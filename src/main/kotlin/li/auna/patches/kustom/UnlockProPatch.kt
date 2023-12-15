package li.auna.patches.kustom

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import li.auna.patches.kustom.fingerprints.HasPurchasedFingerprint
import li.auna.patches.kustom.fingerprints.IsPurchaseValidFingerprint

@Patch(
    name = "Unlock Pro",
    description = "Unlocks pro features",
    compatiblePackages = [
        CompatiblePackage("org.kustom.wallpaper",["3.73b314511"]),
        CompatiblePackage("org.kustom.widget",["3.73b314510"]),
        CompatiblePackage("org.kustom.lockscreen",["3.73b314511"]),
    ],
)
@Suppress("unused")
object UnlockProPatch : BytecodePatch(
    setOf(HasPurchasedFingerprint, IsPurchaseValidFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        HasPurchasedFingerprint.result?.mutableMethod?.apply {
            val index = implementation!!.instructions.lastIndex - 1
            // Set hasPremium = true.
            replaceInstruction(index, "const/4 v0, 0x1")
        } ?: throw HasPurchasedFingerprint.exception

        IsPurchaseValidFingerprint.result?.mutableMethod?.apply {
            val index = implementation!!.instructions.lastIndex - 3
            // Set isPremiumValid = true.
            replaceInstruction(index, "const/4 v0, 0x1")
        } ?: throw IsPurchaseValidFingerprint.exception
    }
}
