package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.activities.MainActivity;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Duda on 08/11/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        gerarNotificacao(context, new Intent(context, MainActivity.class),
                "Novo Alerta", "Alerta de Vigência", "Entre em contato com o segurado!");
    }

    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao) {

//        String nomeSegurado = intent.getExtras().getString("nomeSegurado");
//        String telefoneSegurado= intent.getExtras().getString("telefoneSegurado");
//        String nomeSeguradora= intent.getExtras().getString("nomeSeguradora");
//        String tipoApolice= intent.getExtras().getString("tipoApolice");
//        String vencimentoApolice= intent.getExtras().getString("vencimentoApolice");
//
//        int telefone = Integer.parseInt(telefoneSegurado);

        Uri uri = Uri.parse("tel:" + 991143083);
        Intent callIntent = new Intent(Intent.ACTION_CALL, uri);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent notificationPending = PendingIntent.getActivity(context, 0, intent, 0);
        PendingIntent callPending = PendingIntent.getActivity(context, 0, callIntent, 0);

        Notification notificationAction = new Notification.Builder(context)
                .setTicker(ticker)
                .setContentTitle(titulo)
                .setContentText(descricao)
                .setSmallIcon(R.drawable.ic_notification_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_faccilita_logo))
                .setContentIntent(notificationPending)
                .addAction(R.drawable.ic_phone_white_24dp, "Ligar", callPending)
                .addAction(R.drawable.ic_add_message_white_24dp, "SMS", notificationPending)
                .build();

        notificationAction.vibrate = new long[]{150, 300, 150, 600};
        notificationAction.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher_faccilita_logo, notificationAction);

        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone toque = RingtoneManager.getRingtone(context, som);
        toque.play();

//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("+55"+"11991143083", null,
//                "Prezado Cliente " + ", sua apólice da seguradora X vence dia tal." +
//                        "Tenho uma ótima proposta, por favor entre em contato." +
//                        "celular / email",
//                null, null);

    }
}
