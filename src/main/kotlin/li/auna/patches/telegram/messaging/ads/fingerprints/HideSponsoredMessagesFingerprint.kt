package li.auna.patches.telegram.messaging.ads.fingerprints
import app.revanced.patcher.fingerprint.MethodFingerprint

internal object HideSponsoredMessagesFingerprint : MethodFingerprint(
    returnType = "V",
    customFingerprint = { methodDef, classDef ->
        // org.telegram.ui.ChatActivity -> Lorg/telegram/ui/ChatActivity;
        classDef.type.endsWith("Lorg/telegram/ui/ChatActivity;") &&
                methodDef.name == "addSponsoredMessages"
    }
)