<html>
<head>
    <title>HELLO</title>
    <script src="https://apps-static.flock.co/js-sdk/0.1.0/flock.js"></script>
    <script src="https://5e97ec15.ngrok.io/resources/Pizzicato.min.js"></script>
    <script src="https://5e97ec15.ngrok.io/resources/jquery.min.js"></script>
    <link href="https://5e97ec15.ngrok.io/resources/modal_buttons.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="wrapper">
    <textarea class="text-box" id="text-sending"></textarea>
    <div class="btn-container">
        <label class="switch"><input id = "sound-btn" type="checkbox" onclick="recordSound()" value="begin"><div class="slider round"></div></label>
        <button class="send-btn" onclick="mySend()">Send</button>
        <button class="close-btn" onclick="myClose()">Close</button>
    </div>
</div>
</body>
<script>

    <%  String sendingEntity = (String) request.getAttribute("sending-entity");
        String receivingEntity = (String) request.getAttribute("receiving-entity");%>

    var sender = "<%=sendingEntity%>";
    var receiver = "<%=receivingEntity%>";
    var noteCreated = false;

    function isStorageAvailable() {
        try {
            localStorage.setItem("test", "test");
            return true;
        } catch (e) {
            return false;
        }
    }

    if(isStorageAvailable() && localStorage.getItem(sender + "@#@" + receiver)) {
        document.getElementById("text-sending").value += localStorage.getItem(sender + "@#@" + receiver);
    }

    window.addEventListener('unload', function(event) {
        if(isStorageAvailable() && noteCreated){
            localStorage.removeItem(sender + "@#@" + receiver);
        }
        else if(isStorageAvailable())   {
            localStorage.setItem(sender + "@#@" + receiver, document.getElementById("text-sending").value);
        }
    });

    function myClose()    {
        var text = document.getElementById("text-sending").value;
        flock.close();
    }

    function mySend() {
        var text = document.getElementById("text-sending").value;
        $.ajax({
            url: "https://5e97ec15.ngrok.io/api/send?sender=" + sender + "&receiver=" + receiver + "&toSend=" + text, success: function(result){
                noteCreated = true;
                myClose();
            }
        });
    }

    var voice;

    function recordSound() {

        var el = document.getElementById("sound-btn");

        if(el.value == "on" || el.value == "begin") {
            voice = new Pizzicato.Sound({
                source: 'input',
                options: {volume: 0.3}
            });
            el.value = "off";
        }else if(el.value == "off"){
            el.value = "on";
            $.ajax({
                url: "https://5e97ec15.ngrok.io/api/recording/stop?sound=" + voice, success: function(result){
                    $("#text-sending").html(result);
                }
            });
            voice.play();
        }
    }

</script>
</html>