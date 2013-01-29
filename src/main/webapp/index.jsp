<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Social Network</title>
    <script type="text/javascript" src="js/jquery-1.9.js"></script>
    <script type="text/javascript">
        function signIn() {
            FB.login(function (response) {
                if (response.authResponse) {
                    onUser(response.authResponse.accessToken);
                } else {
                    tryAgain();
                }
            }, {scope: 'email'});
        }

        function signOut() {
            FB.logout(function (response) {
                $('#greet').html('See you!');
                $('.on-off').toggle();
            });
            closeAccountForm();
        }

        function onUser(accessToken) {
            FB.api('/me', function (response) {
                $('#greet').html('Hi, ' + response.first_name + '!');
                $('.on-off').toggle();
                $('#account-details').html('<form id="new-account-form"><table style="width: 100%; border-spacing: 0;">' +
                        '<tr><td style="width: 27%; padding: 0;">name:</td><td style="width: 73%; padding: 0;"><input type="text" name="name" style="width: 100%; margin: 0; border: 0;" value="' + response.name + '"/></td></tr>' +
                        '<tr><td style="width: 27%; padding: 0;">email:</td><td style="width: 73%; padding: 0;"><input type="text" name="email" style="width: 100%; margin: 0; border: 0;" value="' + response.email + '"/></td></tr>' +
                        '<tr><td style="width: 27%; padding: 0;">password:</td><td style="width: 73%; padding: 0;"><input type="password" name="password" style="width: 100%; margin: 0; border: 0;" value="**********"/></td></tr></table></form>'
                );
                $('#facebook').show();
                showAccountForm();
            });
        }

        function tryAgain() {
            $('#greet').html('Try again!');
        }

        function showAccountForm() {
            $("#account").show();
            $('#account-form').animate({
                width: '250px',
                height: '55px',
                opacity: '1'
            }, 150, function () {
                $('#account-details,#close-button,#create-button').animate({opacity: '1'}, 200);
            });
        }

        function closeAccountForm(complete) {
            $('#account-details,#close-button,#create-button').animate({opacity: '0'}, 200, function () {
                $('#account-form').animate({
                    width: '0',
                    height: '0',
                    opacity: '0'
                }, 150, function () {
                    $("#account").hide();
                    if (complete) complete();
                });
            });
        }

        function createAccount() {
            closeAccountForm(function () {
                $.post('account/create', $('#new-account-form').serialize(), function () {
                    $('#message').fadeIn();
                    setTimeout(function () {
                        $('#message').fadeOut();
                    }, 1000);
                });
            });
        }
    </script>
<body>

<div id="fb-root"></div>
<script>
    window.fbAsyncInit = function () {
        FB.init({
            appId: '413685252046415',
            channelUrl: 'http://localhost:8080/channel.html',
            status: true,
            cookie: true,
            xfbml: true
        });
        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') {
                onUser(response.authResponse.accessToken);
            } else {
                $('#greet').html('Sign In');
                $('#facebook').show();
            }
        });
    };

    (function (d) {
        var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
        if (d.getElementById(id)) {
            return;
        }
        js = d.createElement('script');
        js.id = id;
        js.async = true;
        js.src = '//connect.facebook.net/en_US/all.js';
        ref.parentNode.insertBefore(js, ref);
    }(document));
</script>

<div style="margin-left: 1080px; position: relative;">
    <div id="facebook" style="height: 32px; overflow: hidden; display: none;">
        <div class="on-off" style="width: 32px; height: 32px; float: left;">
            <img src="img/off.png" style="cursor: pointer;" onclick="signIn();"/>
        </div>
        <div class="on-off" style="width: 32px; height: 32px; float: left; display: none;">
            <img src="img/on.png" style="cursor: pointer;" onclick="signOut();"/>
        </div>
        <div id="greet" style="margin-top: 9px; margin-left: 10px; float: left;"></div>
    </div>
</div>

<div id="account" style="margin: 10px 0 0 1096px; display: none;">
    <div id="close-button" style="margin-left: 225px; opacity: 0;">
        <img src="img/x.png" style="cursor: pointer;" onclick="closeAccountForm();"/>
    </div>
    <div id="account-form" style="width: 0; height: 0; opacity: 0; border: solid #a1aca8 1px;">
        <div style="padding: 5px;">
            <div id="account-details" style="font-size: 13px; opacity: 0;"></div>
        </div>
    </div>
    <div id="create-button" style="opacity: 0;">
        <img src="img/v.jpg" style="cursor: pointer;" onclick="createAccount();"/>
    </div>
</div>

<div id="message" style="margin: 10px 0 0 1100px; font-size: 13px; display: none;">account created</div>

<div>
    <input type="button" value="sdf" onclick="showAccountForm();"/>
</div>

</body>
</html>