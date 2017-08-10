package grails3.apns

import grails.transaction.Transactional
import com.turo.pushy.apns.*;
import com.turo.pushy.apns.util.*;
import io.netty.util.concurrent.Future;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

@Transactional
class ApnsService {

  def sendPushNotification(String message, List tokenList) {
    final ApnsClient apnsClient = new ApnsClientBuilder()
    .setClientCredentials(new File("/path/YOURPUSHCERTIFICATE.p12"), "gewr")
    .build();

    apnsClient.setMetricsListener(new NoopMetricsListener());

    final Future<Void> connectFuture = apnsClient.connect(ApnsClient.PRODUCTION_APNS_HOST); // DEVELOPMENT_APNS_HOST for development environment
    connectFuture.await();

    final SimpleApnsPushNotification pushNotification;

    tokenList.each { String token ->
      final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
      payloadBuilder.setAlertBody(message);

      final String payload = payloadBuilder.buildWithDefaultMaximumLength();
      final String tkn = TokenUtil.sanitizeTokenString(token);

      pushNotification = new SimpleApnsPushNotification(tkn, "com.xxxxxx", payload); // App bundle ID
    }

    final Future<PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture =
            apnsClient.sendNotification(pushNotification);

    try {
        final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                sendNotificationFuture.get();

        if (pushNotificationResponse.isAccepted()) {

        } else {
            System.out.println("Notification rejected by the APNs gateway: " +
                    pushNotificationResponse.getRejectionReason());

            if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {

            }
        }
    } catch (final ExecutionException e) {
        System.err.println("Failed to send push notification.");
        e.printStackTrace();

        if (e.getCause() instanceof ClientNotConnectedException) {
            apnsClient.getReconnectionFuture().await();
        }
    }

    final Future<Void> disconnectFuture = apnsClient.disconnect();
    disconnectFuture.await();
  }

    def serviceMethod() {

    }
}
