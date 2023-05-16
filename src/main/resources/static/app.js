let isBurgerOpen = true;
window.addEventListener('load', removeTable);

function removeTable(){
    let hero = document.querySelector('.hero');
    let table = hero.querySelector('table');
    if(table){
        table.remove();
    }
}
function makeNav(element, list){

    let leftNav = document.createElement('div');
    let centerNav = document.createElement('div');
    let rightNav = document.createElement('div');

    leftNav.className = 'left-nav';
    centerNav.className = 'center-nav';
    rightNav.className = 'right-nav';

    let navLogo = document.createElement('div');
    let navUL = document.createElement('ul');

    leftNav.appendChild(navLogo);
    centerNav.appendChild(navUL);

    element.appendChild(leftNav);
    element.appendChild(centerNav);
    element.appendChild(rightNav);

    makeAchorList(document.querySelector('.nav-bar .center-nav ul'), list);
}
function makeBurger(element, openFunction, closeFunction, name){

    let burgerBtn = document.createElement('button');
    let burgerDiv = document.createElement('div');
    let topLine = document.createElement('span');
    let centerLine = document.createElement('span');
    let bottomLine = document.createElement('span');

    topLine.id = 'topLine';
    centerLine.id = 'centerLine';
    bottomLine.id = 'bottomLine';

    burgerDiv.appendChild(topLine);
    burgerDiv.appendChild(centerLine);
    burgerDiv.appendChild(bottomLine);
    burgerBtn.appendChild(burgerDiv);

    element.appendChild(burgerBtn);

    function makeBurgerClose(element){
        document.querySelector('.'+element.className+' button div #topLine').style.cssText = 'width:50%; transition: width 1s ease;';
        document.querySelector('.'+element.className+' button div #bottomLine').style.cssText = "align-self: flex-end; width: 50%; transition: width 1s ease;";

        setTimeout(()=>{

            document.querySelector('.'+element.className+' button div #topLine').style.cssText = "width:50%;transform-origin:left; margin-left: 7px; transition: transform 1s ease; transform: rotate(45deg)"
            document.querySelector('.'+element.className+' button div #centerLine').style.cssText = "transition: transform 1s ease; transform: rotate(-45deg)"
            document.querySelector('.'+element.className+' button div #bottomLine').style.cssText = "align-self: flex-end; width: 50%; transform-origin:right; margin-right: 7px; transition: transform 1s ease; transform: rotate(45deg"
        },1000)

        setTimeout(()=>{
            document.querySelector('.'+element.className+' button').disabled = false;
        },2000);

        setTimeout(()=>{
            openFunction();
        },2000);

    }
    function makeBurgerLines(){
        document.querySelector('.'+element.className+' button div #topLine').style.cssText = "width:50%;transform-origin:left; margin-left: 7px; transition: transform 1s ease; transform: rotate(0deg)"
        document.querySelector('.'+element.className+' button div #centerLine').style.cssText = "transition: transform 1s ease; transform: rotate(0deg)"
        document.querySelector('.'+element.className+' button div #bottomLine').style.cssText = "align-self: flex-end; width: 50%; transform-origin:right; margin-right: 7px; transition: transform 1s ease; transform: rotate(0deg"

        setTimeout(()=>{
            document.querySelector('.'+element.className+' button div #topLine').style.cssText = 'width:100%; transition: width 1s ease;';
            document.querySelector('.'+element.className+' button div #bottomLine').style.cssText = "align-self: flex-end; width: 100%; transition: width 1s ease;";
        },1000);

        setTimeout(()=>{
            document.querySelector('.'+element.className+' button').disabled = false;
        },2000);

        setTimeout(()=>{
            closeFunction();
        },2000);
    }

    document.querySelector('.'+element.className +' button').addEventListener('click', ()=>{
        if(isBurgerOpen){
            document.querySelector('.'+element.className +' button').disabled = true;
            makeBurgerClose(element);
            isBurgerOpen = false
        }
        else if (!isBurgerOpen){
            document.querySelector('.'+element.className +' button').disabled = true;
            makeBurgerLines(element);
            isBurgerOpen=true;
        }
    });
    burgerBtn.className = name;
}
function openMenu(){
    console.log('oppen');
}
function closeMenu(){
    console.log('close')
}
function makeLogo(element, img){

}
function makeList(element, list){
    list.forEach(e => {
        let liElement = document.createElement('li');
        liElement.textContent = e;
        liElement.className = e;
        element.appendChild(liElement);
    });
    element.style.cssText = ''
}
async function makeTableFromJson(tableParent,endpoint){
    removeTable();
    console.log(endpoint)
    let data = await fetchJson(endpoint);

    let table = document.createElement('table');
    let tHead = document.createElement('thead');
    let tr = document.createElement('tr');


    let tBody = document.createElement('tbody');

    const list = [];

    Object.keys(data[0]).forEach(column=>{
        let th = document.createElement('th');
        th.textContent = column;
        tr.appendChild(th);
    });

    data.forEach(element =>{
        let tBodyRow = document.createElement('tr');

        Object.values(element).forEach(column =>{
            let td = document.createElement('td');
            td.textContent = column;
            tBodyRow.appendChild(td);
        });

        let button1 = document.createElement('button');
        button1.textContent = 'UPDATE';
        button1.addEventListener('click',()=>{
            console.log('UPDATE USER ' + tBodyRow.cells[0].textContent)
        });

        let button2 = document.createElement('button');
        button2.textContent = 'REMOVE';
        button2.addEventListener('click',()=>{
            console.log('REMOVE USER ' + tBodyRow.cells[0].textContent)
        });

        tBodyRow.appendChild(button1);
        tBodyRow.appendChild(button2);
        tBody.appendChild(tBodyRow);
    });

    tHead.appendChild(tr);
    table.appendChild(tHead);
    table.appendChild(tBody);
    document.querySelector('.hero').appendChild(table);
    // table.appendChild(tHead);
    //tableParent.appendChild(table);
}
async function fetchJson(endpoint){
    const response = await fetch(endpoint);
    const data = await response.json();
    return data;
}
async function makeListFromJson(ul,endpoint){
    let data = await fetchJson(endpoint);
    const list = [];
    data.forEach(element =>{
        list.push(Object.values(element));
        console.log(list)
    });

    makeList(document.querySelector(ul), list)
}
function menuPopOutL2R(element){
    let menu = document.querySelector('.hero .menu' );
    transitionWidth(menu, '100vw');
}
function menuPopInL2R(element){
    let menu = document.querySelector('.hero .menu' );
    transitionWidth(menu, '0vw');
}
function transitionWidth(element, newWidth){
    element.style.width = newWidth;
}
function makeAchorList(element, list){
    list.forEach(e => {
        let liElement = document.createElement('li');
        let a = document.createElement('a');
        a.className = e + '-aTag';
        a.setAttribute('href', '#');
        a.textContent = e;
        liElement.appendChild(a);
        liElement.className = e + '-li';
        element.appendChild(liElement);
    });
    element.style.cssText = ''
}
function setHref(aTag, endpoint) {
    if(endpoint == '/index'){
        aTag.setAttribute('href', '/index');
    }
    else {
        aTag.setAttribute('href', '#');
        aTag.setAttribute('onclick', 'event.preventDefault(); makeTableFromJson(document.querySelector(".hero"), "' + endpoint + '")');
    }
}

var myList = [ "HOME","CUSTOMERS", "ITEMS", "ORDERS" ];

makeNav(document.querySelector('.nav-bar'), myList);

makeBurger(document.querySelector('.left-nav'), ()=> menuPopOutL2R(document.querySelector('.hero')), ()=>menuPopInL2R(document.querySelector('.hero div')), 'leftMenu');

setHref(document.querySelector('.HOME-aTag'), '/index');
setHref(document.querySelector('.CUSTOMERS-aTag'), '/customers');
setHref(document.querySelector('.ITEMS-aTag'), '/items');
setHref(document.querySelector('.ORDERS-aTag'), '/orders')

//makeTableFromJson(document.querySelector('.hero'), '/customers')
//makeLogo(document.querySelector('.nav-bar .left-nav'))
