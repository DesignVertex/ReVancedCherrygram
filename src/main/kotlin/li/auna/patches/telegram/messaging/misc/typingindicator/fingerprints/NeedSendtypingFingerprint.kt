package li.auna.patches.telegram.messaging.misc.typingindicator.fingerprints
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal object NeedSendtypingFingerprint : MethodFingerprint(
    returnType = "V",
    customFingerprint = { methodDef, _ ->
                methodDef.name == "needSendTyping"
    },
    opcodes = listOf(
        Opcode.IGET_OBJECT,
        Opcode.INVOKE_STATIC
    )
)