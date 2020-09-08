let choose_btn = document.querySelector('.choose-btn');
let google_item = document.querySelector('.google');
let yandex_item = document.querySelector('.yandex');
let duck_item = document.querySelector('.duck');
let bing_item = document.querySelector('.bing');
let naver_item = document.querySelector('.naver');
let search_form = document.querySelector('.search-system-form');
let search_field = document.querySelector('.search-field');

function form_edit(action, name) {
    search_form.setAttribute('action', action);
    search_field.setAttribute('name', name);
}

google_item.onclick = function() {
    choose_btn.textContent = 'Google';
    form_edit('https://www.google.com/search', 'q');
}

yandex_item.onclick = function() {
    choose_btn.textContent = 'Yandex';
    form_edit('https://yandex.ru/search', 'text');
}

duck_item.onclick = function() {
    choose_btn.textContent = 'DuckDuckGo';
    form_edit('https://duckduckgo.com', 'q');
}

bing_item.onclick = function() {
    choose_btn.textContent = 'Bing';
    form_edit('https://www.bing.com/search', 'q');
}

naver_item.onclick = function() {
    choose_btn.textContent = 'Naver';
    form_edit('https://search.naver.com/search.naver', 'query');
}