package com.martyngomez.firebasedemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.martyngomez.firebasedemo.model.PlatziNotificacion;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// Servicio que Muestra el mensaje

public class PlatziFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "PlatziMessagingService";
    private static final String KEY_DESCOUNT = "descount_key";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) { // Obtiene Mensaje
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Establece todos los valores obtenidos en un objeto para pasar por parametro
        PlatziNotificacion platziNotificacion = new PlatziNotificacion();
        platziNotificacion.setId(remoteMessage.getFrom()); // Identificador de la notificacion
        platziNotificacion.setTitle(remoteMessage.getNotification().getTitle());
        platziNotificacion.setDescription(remoteMessage.getNotification().getBody());
        platziNotificacion.setDescount(remoteMessage.getData().get(KEY_DESCOUNT));

        showNotification(platziNotificacion);

    }

    private void showNotification(PlatziNotificacion platziNotificacion){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KEY_DESCOUNT, platziNotificacion.getDescount());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Para que se limpie la pantalla cuando tocamos la  notificacion
        // Intent peque queda pendiente
        PendingIntent pendingIntent
                = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);// Reproduce ringtone definido en el sistema
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this) //v4 , Construye objeto notificacion
                .setSmallIcon(R.drawable.ic_star) // Icono
                .setContentTitle(platziNotificacion.getTitle()) //Título
                .setContentText(platziNotificacion.getDescription())
                .setAutoCancel(true) // Notificacion autocancelable
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) //Visibilidad, ej. si esta bloqueado si se muesta o no
                .setSound(defaultSoundUri) // Sonido
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0, notificationBuilder.build());

    }
}
