package li.auna.patches.telegram.messaging.timer.fingerprints
import app.revanced.patcher.fingerprint.MethodFingerprint

internal object NoMediaTimerLimitFingerprint : MethodFingerprint(
    returnType = "V",
    customFingerprint = { _, classDef ->
        classDef.type.endsWith("Lorg/telegram/tgnet/TLRPC\$Message;")
    }
)