package li.auna.patches.kustom.fingerprints
import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object IsPurchaseValidFingerprint : MethodFingerprint(
    returnType = "Z",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    customFingerprint = { methodDef, classDef ->
        classDef.type.endsWith("Lorg/kustom/billing/LicenseState;") &&
                methodDef.name == "isValid"
    }
)