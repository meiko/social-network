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

    function facebookLogin(success, failure) {
        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') success(response);
            else failure(response)
        });
    }
</script>