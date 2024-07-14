package li.auna.patches.youtube.layout.largepausebutton

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch

@Patch(
    name = "Large Play/Pause Button",
    description = "Makes the play/pause button 2x larger",
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
                "19.09.38",
                "19.10.39",
                "19.11.43",
                "19.12.41",
                "19.13.37",
                "19.14.43",
                "19.15.36",
                "19.16.39",
            ]
        )
    ],
)

object LargePlayPauseButtonPatch : ResourcePatch(
) {
    override fun execute(context: ResourceContext) {

        context.xmlEditor["res/layout/youtube_controls_button_group_layout.xml"].use { editor ->
           val document = editor.file

           // modify the width and height of player_control_play_pause_replay_button_touch_area element
           val btnTouchArea =  document.getElementsByTagName("FrameLayout").item(0)
            btnTouchArea.attributes.getNamedItem("android:layout_width").nodeValue = "180dp"
            btnTouchArea.attributes.getNamedItem("android:layout_height").nodeValue = "180dp"

            // modify the width and height of player_control_play_pause_replay_button element
            val btn =  document.getElementsByTagName("com.google.android.libraries.youtube.common.ui.TouchImageView").item(0)
            btn.attributes.getNamedItem("android:layout_width").nodeValue = "112dp"
            btn.attributes.getNamedItem("android:layout_height").nodeValue = "112dp"
            // modify the padding of player_control_play_pause_replay_button element. default is 8dp
            btn.attributes.getNamedItem("android:padding").nodeValue = "16dp"

        }

    }
}