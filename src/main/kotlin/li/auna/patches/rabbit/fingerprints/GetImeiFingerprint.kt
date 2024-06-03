package li.auna.patches.rabbit.fingerprints
import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
internal object GetImeiFingerprint : MethodFingerprint(
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    strings = listOf("context"),
    customFingerprint = { methodDef, classDef ->
        methodDef.name == "getImei" && classDef.type.endsWith("Ltech/rabbit/r1launcher/settings/utils/SystemControllerUtil;")
    }
)