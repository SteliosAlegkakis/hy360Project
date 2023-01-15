function viewMenu(){
    let menu = document.getElementById("menu");
    if(menu.style.display === 'none') menu.style.display = "flex";    
    else menu.style.display = 'none';
}

function viewForm(form_name){
    let form = document.getElementById(form_name);
    document.getElementById(form_name+"_success").style.display = 'none';
    document.getElementById(form_name+"_fail").style.display = 'none';
    if(form.style.display === 'flex') form.style.display = 'none'
    else {
        let forms = document.getElementsByTagName("form");
        for (let i=0; i<forms.length; i++){
            forms.item(i).style.display = 'none';
        }
        form.style.display = 'flex';
        document.getElementById('menu').style.display = 'none';
    }
}

function viewElement(element_id,calling_element,text){
    let element = document.getElementById(element_id);
    if(element.style.display === 'none') {
        element.style.display = 'flex';
        text = text+" ^";
    }
    else{
        element.style.display = 'none';
        text = text + " ˅"
    }
    calling_element.innerHTML = text;
}

function server_request(form_name,method,request){
    let data = $("#"+form_name).serialize()+"&request="+request;
    console.log(data);
    let xml_request = new XMLHttpRequest();
    xml_request.onload = function (){
        if(xml_request.readyState === 4 && xml_request.status === 200) {
            document.getElementById(form_name + "_success").style.display = 'flex';
            document.getElementById(form_name).style.display = 'none';
        }
        else if(xml_request.status != 200) {
            document.getElementById(form_name + "_fail").style.display = 'block';
        }
    }
    xml_request.open(method, 'MyServlet?'+data);
    xml_request.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xml_request.send(data);
}