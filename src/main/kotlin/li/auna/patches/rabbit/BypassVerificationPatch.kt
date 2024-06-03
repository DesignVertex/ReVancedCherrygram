package li.auna.patches.rabbit

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.removeInstructions
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod.Companion.toMutable
import com.android.tools.smali.dexlib2.builder.MutableMethodImplementation
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import li.auna.patches.rabbit.fingerprints.*
import li.auna.patches.rabbit.fingerprints.GetDeviceIDFingerprint
import li.auna.patches.rabbit.fingerprints.GetImeiFingerprint
import li.auna.patches.rabbit.fingerprints.GetOSVersionFingerprint
import li.auna.patches.rabbit.fingerprints.GotoConnectNetworkFingerprint
import li.auna.patches.rabbit.fingerprints.OnKeyUpFingerprint

@Patch(
    name = "Bypass Verification",
    description = "Bypasses the verification process to allow the app to run on any device. + You need to adb shell pm grant tech.rabbit.r1launcher.r1 android.permission.WRITE_SECURE_SETTINGS after installation.",
    compatiblePackages = [
        CompatiblePackage("tech.rabbit.r1launcher.r1"),
    ],
)
@Suppress("unused")
object BypassVerificationPatch : BytecodePatch(
    setOf(
        GetOSVersionFingerprint,
        GetImeiFingerprint,
        GetDeviceIDFingerprint,
        GotoConnectNetworkFingerprint,
        OnKeyUpFingerprint,
        OnKeyDownFingerprint,
        HookAccountKeyFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {
        GetOSVersionFingerprint.result?.mutableMethod?.apply {
            replaceInstructions(
                0, """
                        const-string p0, "rabbit_OS_v0.8.86_20240523151103"
                        return-object p0
                        """
            )
        } ?: throw GetOSVersionFingerprint.exception

        GetImeiFingerprint.result?.mutableMethod?.apply {
            replaceInstructions(
                0, """
                        const-string p0, "${generateIMEI()}"
                        return-object p0
                        """
            )
        } ?: throw GetImeiFingerprint.exception

        GetDeviceIDFingerprint.result?.mutableMethod?.apply {
            replaceInstructions(
                0, """
                        const-string p0, "${generateIMEI()}"
                        return-object p0
                        """
            )
        } ?: throw GetDeviceIDFingerprint.exception

        GotoConnectNetworkFingerprint.result?.mutableMethod?.apply {
            replaceInstructions(
                0, """
                        const-string v0, "rabbit explode"
                        invoke-virtual {p0, v0}, Ltech/rabbit/r1launcher/initstep/InitStepActivity;->connectWifiSuccess(Ljava/lang/String;)V
                        return-void
                        """
            )
        } ?: throw GotoConnectNetworkFingerprint.exception

        val onKeyUpMethodResult = OnKeyUpFingerprint.result ?: throw OnKeyUpFingerprint.exception

        onKeyUpMethodResult.mutableClass.methods.remove(OnKeyUpFingerprint.result?.mutableMethod)

        val onKeyUpMethod = ImmutableMethod(
            onKeyUpMethodResult.classDef.type,
            onKeyUpMethodResult.method.name,
            onKeyUpMethodResult.method.parameters,
            onKeyUpMethodResult.method.returnType,
            onKeyUpMethodResult.method.accessFlags,
            onKeyUpMethodResult.method.annotations,
            onKeyUpMethodResult.method.hiddenApiRestrictions,
            MutableMethodImplementation(6)
        ).toMutable().apply {
            addInstructions(
                """
                    const/4 p0, -0x1
                    sput p0, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->lastKey:I               
                    sput p1, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->lastUpKey:I                
                    const/16 p0, 0x18                
                    if-eq p1, p0, :cond_19                
                    const/16 p0, 0x19                
                    if-eq p1, p0, :cond_19                
                    const/16 p0, 0x13                
                    if-eq p1, p0, :cond_1b                
                    const/16 p0, 0x14                
                    if-eq p1, p0, :cond_1b                
                    packed-switch p1, :pswitch_data_46                
                    goto :goto_3c                
                    :cond_19
                    const/16 p1, 0x1a                
                    :cond_1b
                    :pswitch_1b
                    sget-object p0, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->TAG:Ljava/lang/String;                
                    new-instance p2, Ljava/lang/StringBuilder;                
                    const-string v0, "Volume key up "                
                    invoke-direct {p2, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V                
                    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;                
                    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;                
                    move-result-object p2                
                    invoke-static {p0, p2}, Ltech/rabbit/common/utils/RLog;->i(Ljava/lang/String;Ljava/lang/String;)V                
                    sget-object p0, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->deviceEvents:LDeviceEventsDispatcher;                
                    if-eqz p0, :cond_3e                
                    int-to-long v0, p1                
                    new-instance p2, Lkotlin/collections/q;                
                    const/4 v2, 0x3                
                    invoke-direct {p2, p1, v2}, Lkotlin/collections/q;-><init>(II)V                
                    invoke-virtual {p0, v0, v1, p2}, LDeviceEventsDispatcher;->onKeyUp(JLkotlin/jvm/functions/Function1;)V                
                    :goto_3c
                    const/4 p0, 0x1                
                    return p0                
                    :cond_3e
                    const-string p0, "deviceEvents"                
                    invoke-static {p0}, Lio/sentry/android/core/internal/util/c;->b0(Ljava/lang/String;)V                
                    const/4 p0, 0x0                
                    throw p0                
                    nop                
                    :pswitch_data_46
                    .packed-switch 0x18
                        :pswitch_1b
                        :pswitch_1b
                        :pswitch_1b
                    .end packed-switch
                """.trimIndent()
            )
        }

        onKeyUpMethodResult.mutableClass.methods.add(onKeyUpMethod)

        val onKeyDownMethodResult = OnKeyDownFingerprint.result ?: throw OnKeyDownFingerprint.exception

        onKeyDownMethodResult.mutableClass.methods.remove(OnKeyDownFingerprint.result?.mutableMethod)

        val onKeyDownMethod = ImmutableMethod(
            onKeyDownMethodResult.classDef.type,
            onKeyDownMethodResult.method.name,
            onKeyDownMethodResult.method.parameters,
            onKeyDownMethodResult.method.returnType,
            onKeyDownMethodResult.method.accessFlags,
            onKeyDownMethodResult.method.annotations,
            onKeyDownMethodResult.method.hiddenApiRestrictions,
            MutableMethodImplementation(6)
        ).toMutable().apply {
            addInstructions(
                """
                    const/16 p0, 0x18
                    if-eq p1, p0, :cond_14               
                    const/16 p0, 0x19                
                    if-eq p1, p0, :cond_14                
                    const/16 p0, 0x13                
                    if-eq p1, p0, :cond_16                
                    const/16 p0, 0x14                
                    if-eq p1, p0, :cond_16                
                    packed-switch p1, :pswitch_data_3a                
                    goto :goto_37                
                    :cond_14
                    const/16 p1, 0x1a                
                    :cond_16
                    :pswitch_16
                    sget p0, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->lastKey:I                
                    if-eq p0, p1, :cond_37                
                    sget-object p0, Ltech/rabbit/r1launcher/wss/RabbitEngine;->INSTANCE:Ltech/rabbit/r1launcher/wss/RabbitEngine;                
                    invoke-virtual {p0}, Ltech/rabbit/r1launcher/wss/RabbitEngine;->sendClear()V                
                    sput p1, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->lastKey:I                
                    sget-object p0, Ltech/rabbit/r1launcher/rabbit/KeyEventHandler;->deviceEvents:LDeviceEventsDispatcher;                
                    if-eqz p0, :cond_30                
                    int-to-long v0, p1                
                    new-instance p2, Lkotlin/collections/q;                
                    const/4 v2, 0x2                
                    invoke-direct {p2, p1, v2}, Lkotlin/collections/q;-><init>(II)V                
                    invoke-virtual {p0, v0, v1, p2}, LDeviceEventsDispatcher;->onKeyDown(JLkotlin/jvm/functions/Function1;)V                
                    goto :goto_37                
                    :cond_30
                    const-string p0, "deviceEvents"                
                    invoke-static {p0}, Lio/sentry/android/core/internal/util/c;->b0(Ljava/lang/String;)V                
                    const/4 p0, 0x0                
                    throw p0                
                    :cond_37
                    :goto_37
                    const/4 p0, 0x1                
                    return p0                
                    nop                
                    :pswitch_data_3a
                    .packed-switch 0x18
                        :pswitch_16
                        :pswitch_16
                        :pswitch_16
                    .end packed-switch
                    """
            )
        }

        onKeyDownMethodResult.mutableClass.methods.add(onKeyDownMethod)

        val hookAccountKeyFingerprint = HookAccountKeyFingerprint.result ?: throw HookAccountKeyFingerprint.exception

        val index = hookAccountKeyFingerprint.scanResult.stringsScanResult!!.matches.last().index

        HookAccountKeyFingerprint.result?.mutableMethod?.apply {
                replaceInstruction(
                    index, """
                        const-string p4, "_eXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYX=="                        
                        """
                )
                removeInstructions(index + 2, 3)
            } ?: throw HookAccountKeyFingerprint.exception
    }
}

fun calculateChecksum(imeiWithoutChecksum: String): String {
    val imeiArray = imeiWithoutChecksum.map { it.toString().toInt() }
    var sum = 0
    var double = false

    for (digit in imeiArray) {
        if (double) {
            var temp = digit * 2
            if (temp > 9) {
                temp -= 9
            }
            sum += temp
        } else {
            sum += digit
        }
        double = !double
    }

    val checksum = (10 - (sum % 10)) % 10
    return checksum.toString()
}

fun generateIMEI(): String {
    val TAC = "35847631"
    val serialNumberPrefix = "00"
    var serialNumber = serialNumberPrefix

    repeat(4) {
        serialNumber += (0..9).random()
    }

    val imeiWithoutChecksum = TAC + serialNumber
    val checksum = calculateChecksum(imeiWithoutChecksum)
    val generatedIMEI = imeiWithoutChecksum + checksum

    return generatedIMEI
}