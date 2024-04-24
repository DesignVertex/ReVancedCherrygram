package li.auna.patches.telegram.messaging.misc.deletedmessages.fingerprints
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal object MarkMessagesAsDeletedFingerprint2 : MethodFingerprint(
    customFingerprint = { methodDef, classDef ->
        classDef.type.endsWith("Lorg/telegram/messenger/MessagesStorage;") &&
                methodDef.name == "markMessagesAsDeletedInternal"
    },
    opcodes = listOf(
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.NEW_INSTANCE,
        Opcode.INVOKE_DIRECT
    )
)