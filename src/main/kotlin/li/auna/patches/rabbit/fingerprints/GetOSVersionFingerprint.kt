package li.auna.patches.rabbit.fingerprints
import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object GetOSVersionFingerprint : MethodFingerprint(
    accessFlags = AccessFlags.PRIVATE or AccessFlags.FINAL,
    strings = listOf("DISPLAY"),
    customFingerprint = { methodDef, classDef ->
        methodDef.name == "getOSVersion" && classDef.type.endsWith("Ltech/rabbit/r1launcher/RLApp;")
    }
)