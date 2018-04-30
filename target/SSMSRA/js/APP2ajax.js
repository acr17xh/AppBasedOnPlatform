$(document).ready(function () {

    $("#getdata").click(function () {
        //Create the "Searching ended" <P> to mark the position, helping insert the json data
        var ending = document.createElement("p");
        var ending_node = document.createTextNode("Searching ended");
        var mydiv = document.getElementById("myDiv");
        mydiv.appendChild(ending); ending.appendChild(ending_node);
        $("#myDiv > p").attr({"id":"ending"}) //add a id for next process,insert json data

        //Creat title of this div
        $("#myDiv > p").before("<h2>Restaurant Information</h2>");

        $.ajax({
            url:"${contextPath}/home/detail",
            type:'GET',
            dataType:"json",
            success:function (data) {
                var Number = 0;

                //data is returned by @responseBody, it's a javascript object(json object)
                //遍历整个json获取嵌套数据

                for(restaurant in data) {
                    Number = Number + 1;

                    var disArea = document.createElement("div");
                    disArea.style.position = "relative";
                    disArea.style.borderBottom = "solid black 1px";
                    disArea.style.height = "220px";

                    var name = document.createElement("p");
                    var nameEntity = document.createTextNode(Number+"."+data[restaurant].name+":");
                    name.appendChild(nameEntity);
                    disArea.appendChild(name);

                    //获取地址
//						var address = document.createElement("p");
//						var addressEntity = document.createTextNode("Address: "+data[restaurant].vicinity );
//						address.appendChild(addressEntity);
//						disArea.appendChild(address);
                    //获取rating
//                        var rate = document.createElement("p");
//                        var rateEntity = document.createTextNode("Rating: "+data[restaurant].rating );
//                        rate.appendChild(rateEntity);
//                        disArea.appendChild(rate);

                    //Click button to return detail information model
                    var butt = document.createElement("button");
                    butt.style.width="100px";butt.style.height="40px";
                    butt.id = Number;
                    butt.type = "submit";
                    butt.innerHTML = butt.id;
                    butt.value = data[restaurant].name;
                    butt.name = "restname"
                    butt.addEventListener("click",function () {
                        document.getElementById("specific").innerHTML = "";


                        var restname = this.value;
                        $.ajax({
                            url:"${contextPath}/home/RestaurantDetail",
                            type:'POST',
                            data: {"restname":restname},
                            dataType :"json",
                            success:function (data) {

                                //for loop used to traverse although only one object in the json file.
                                for (restaurant_two in data) {

                                    //Second Ajax photo model
                                    var ImgPath = data[restaurant_two].restaurant_photo.photo_reference;
                                    var heightImg = data[restaurant_two].restaurant_photo.height;
                                    var widthImg = data[restaurant_two].restaurant_photo.width;
                                    var GoogleImgPathBig = "https://maps.googleapis.com/maps/api/place/photo?"+
                                        "maxwidth="+ widthImg+
                                        "&maxheight"+ heightImg+
                                        "&photoreference="+ImgPath+
                                        "&key="+"AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU";
                                    function picc() {
                                        var imm = new Image();
                                        imm.src = GoogleImgPathBig;
                                        //style.width则需要添加双引号和px
                                        imm.style.width = "500px";
                                        imm.style.height = "480px";
                                        document.getElementById("specific").appendChild(imm);
                                    }
                                    picc();
                                    //Second Ajax information model.
                                    var specific_rest_name = document.createElement("h2");
                                    var specific_rest_nameEntity = document.createTextNode(data[restaurant_two].name);
                                    var specific_rest_address = document.createElement("p");
                                    var specific_rest_addressEntity = document.createTextNode("Address:"+data[restaurant_two].vicinity);
                                    var specific_rest_rate = document.createElement("p");
                                    var specific_rest_rateEntity = document.createTextNode("Rating"+data[restaurant_two].rating);
                                    specific_rest_name.appendChild(specific_rest_nameEntity);
                                    specific_rest_address.appendChild(specific_rest_addressEntity);
                                    specific_rest_rate.appendChild(specific_rest_rateEntity);
                                    document.getElementById("specific").appendChild(specific_rest_name);
                                    document.getElementById("specific").appendChild(specific_rest_address);
                                    document.getElementById("specific").appendChild(specific_rest_rate);
                                }
                            },
                            error:function () {
                                alert("second read error")
                            }
                        })
                    },false);
                    disArea.appendChild(butt);

                    //Display the necessary image model
                    var PreImgPath = data[restaurant].restaurant_photo.photo_reference;
                    var Maxheight = data[restaurant].restaurant_photo.height;
                    var Maxwidth = data[restaurant].restaurant_photo.width;
                    var GoogleImgPath = "https://maps.googleapis.com/maps/api/place/photo?"+
                        "maxwidth="+ Maxwidth+
                        "&maxheight"+ Maxheight+
                        "&photoreference="+PreImgPath+
                        "&key="+"AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU";
                    //Create a new image to contain picture
                    function pic() {
                        var im = new Image();
                        im.src = GoogleImgPath;
                        //style.width则需要添加双引号和px
                        im.style.width = "80px";
                        im.style.height = "80px";
                        im.style.position = "absolute";
                        im.style.left = "500px";
                        im.style.top = "10px";
                        disArea.appendChild(im);
                    }
                    pic();

                    $("#ending").before(disArea);

                }
            },
            error:function () {
                alert("read error");
            }


        });
    });
});