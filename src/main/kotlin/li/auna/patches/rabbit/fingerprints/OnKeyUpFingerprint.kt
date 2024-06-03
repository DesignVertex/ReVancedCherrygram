package li.auna.patches.rabbit.fingerprints
import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object OnKeyUpFingerprint : MethodFingerprint(
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    strings = listOf("Volume key up "),
    customFingerprint = { methodDef, classDef ->
        methodDef.name == "onKeyUp" && classDef.type.endsWith("Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;")
    }
)