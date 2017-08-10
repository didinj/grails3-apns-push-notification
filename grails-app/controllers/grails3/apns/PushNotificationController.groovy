package grails3.apns

class PushNotificationController {

  def apnsService

  def index() { }

  def sendNotification() {
    if(params.token&&params.message) {
      applePushNotificationService.sendPushNotification(params.message,params.token)

      flash.message = "Push notification sent"
      redirect action:"index"
    } else {
      flash.message = "No token or message found"
      redirect action:"index"
    }
  }
}
