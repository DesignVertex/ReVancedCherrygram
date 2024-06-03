package li.auna.patches.rabbit.fingerprints
import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object GotoConnectNetworkFingerprint : MethodFingerprint(
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    strings = listOf("showType"),
    customFingerprint = { methodDef, classDef ->
        methodDef.name == "gotoConnectNetwork" && classDef.type.endsWith("Ltech/rabbit/r1launcher/initstep/InitStepActivity;")
    }
)