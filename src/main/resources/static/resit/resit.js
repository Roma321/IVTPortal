function setSelection(selectId, requiredToSelect) {
    const $select = document.getElementById(selectId)
    const $options = Array.from($select.options);
    const optionToSelect = $options.find(item => parseInt(item.value) === requiredToSelect);
    optionToSelect.selected = true;
    console.log("xiaoInside")
}
console.log("xiaoOutside")