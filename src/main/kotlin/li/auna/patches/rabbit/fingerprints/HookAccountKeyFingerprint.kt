package li.auna.patches.rabbit.fingerprints
import app.revanced.patcher.fingerprint.MethodFingerprint

internal object HookAccountKeyFingerprint : MethodFingerprint(
    strings = listOf("createWebSocket", "reconnecting ...", "_"),
    customFingerprint = { methodDef, classDef ->
        methodDef.name == "connect" && classDef.type.endsWith("Ltech/rabbit/r1launcher/wss/WssClient;")
    }
)