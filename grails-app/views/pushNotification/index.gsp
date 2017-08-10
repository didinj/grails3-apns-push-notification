<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>APNs Sender</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Send Push Notification</h1>
            <g:if test="${flash.message}">
              <div class="message" role="alert">
                ${flash.message}
              </div>
            </g:if>
            <g:form action="sendNotification">
              <fieldset>
                <div class="fieldcontain">
                  <label for="regid">APNs Token</label>
                  <g:textField name="token" />
                </div>
                <div class="fieldcontain">
                  <label for="body">Message Body</label>
                  <g:textField name="message" />
                </div>
              </fieldset>
              <fieldset>
                <div class="buttons">
                  <g:submitButton class="save" name="submit" value="Send" />
                </div>
              </fieldset>
            </g:form>
        </section>
    </div>
</body>
</html>
