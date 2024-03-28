package li.auna.patches.youtube.layout.hide.subtitlestoast

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import li.auna.patches.youtube.layout.hide.subtitlestoast.fingerprints.HideSubtitlesToastFingerprint
import li.auna.patches.youtube.layout.hide.subtitlestoast.fingerprints.HideSubtitlesToastFingerprint2
import app.revanced.util.exception

@Patch(
    name = "Hide subtitles toast",
    description = "Hides the subtitles toast when toggling subtitles",
    compatiblePackages = [
        CompatiblePackage(
            "com.google.android.youtube",
            [
                "18.32.39",
                "18.37.36",
                "18.38.44",
                "18.43.45",
                "18.44.41",
                "18.45.41",
                "18.45.43",
                "18.48.39",
                "18.49.37",
                "19.01.34",
                "19.02.39",
                "19.03.36",
                "19.04.38",
                "19.05.36",
                "19.06.39",
                "19.07.40",
                "19.08.36",
                "19.09.37"
            ]
        )
    ],
)
object HideSubtitlesToastPatch : BytecodePatch(
    setOf(HideSubtitlesToastFingerprint, HideSubtitlesToastFingerprint2)
) {
    override fun execute(context: BytecodeContext) {

        HideSubtitlesToastFingerprint.result?.mutableMethod?.addInstructions(0, "return-void")
            ?: throw HideSubtitlesToastFingerprint.exception

        HideSubtitlesToastFingerprint2.result?.mutableMethod?.apply {
//            remove the instruction that sets the toast text
            val lastIndex = implementation!!.instructions.lastIndex - 2
            removeInstruction(lastIndex)
        }
    }
}