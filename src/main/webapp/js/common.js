/**
 * replace some html character to with standard expression
 *
 * eg.  '&' --> '&gt'
 *      '<' --> '&lt'    etc
 * @param str
 * @returns {string}
 */
function htmlEncode(str) {
    var s = "";
    if (str.length == 0) return s;
    s = str.replace(/&/g, "&gt;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br>");
    return s;
}

/**
 * add day num to a date
 *
 * @param date the date to add days
 * @param days the number day you want to add
 * @returns {Date}
 */
function addDays(date, days) {
    var _d = new Date();
    _d.setTime(date.getTime() + (1000 * 60 * 60 * 24 * days));
    return _d;
}

/**
 * parse a string pattern date to Date instance object
 *
 * if string is in patterns like below will be parsed successfully
 *
 *      'yyyy-MM-dd'
 *      'yyyy-MM-dd:hh:mm:ss'
 *      'yyyy-MM-dd:hh:mm:ss:SSSXXX'
 *
 * @param str string to be parsed
 * @returns {Date}
 */
function parseDate(str) {
    if (typeof str == 'string') {
        var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);
        if (results && results.length > 3)
            return new Date(parseInt(results[1]), parseInt(results[2]) - 1, parseInt(results[3]));
        results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
        if (results && results.length > 6)
            return new Date(parseInt(results[1]), parseInt(results[2]) - 1, parseInt(results[3]), parseInt(results[4]), parseInt(results[5]), parseInt(results[6]));
        results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);
        if (results && results.length > 7)
            return new Date(parseInt(results[1]), parseInt(results[2]) - 1, parseInt(results[3]), parseInt(results[4]), parseInt(results[5]), parseInt(results[6]), parseInt(results[7]));
    }
    return null;
}

/**
 * add format function for Date.prototype
 * @param fmt
 * @returns {*}
 */
Date.prototype.format = function(fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // Month
        "d+": this.getDate(), // Day
        "h+": this.getHours(), // Hour
        "m+": this.getMinutes(), // Minute
        "s+": this.getSeconds(), // Second
        "q+": Math.floor((this.getMonth() + 3) / 3), // Quarter
        "S": this.getMilliseconds() // MS
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * Cancels the event if it is cancelable, without stopping further propagation of the event.
 *
 * @param e event
 */
function preventDefault(e) {
    e = e || window.event;
    if (e.preventDefault)
        e.preventDefault();
    e.returnValue = false;
}

/**
 * replace a string with 'replaceWith' under the regExp
 *
 * @param reallyDo     RegExp to apply on string
 * @param replaceWith  replace string
 * @param ignoreCase   {boolean} whether ignore case
 * @returns {string}
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
};

/**
 * trim all the blank spaces in string
 *
 * @returns {string}
 */
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * trim start blank spaces in string
 * @returns {string}
 */
String.prototype.ltrim = function() {
    return this.replace(/(^\s*)/g, "");
};

/**
 * trim end blank spaces in string
 * @returns {string}
 */
String.prototype.rtrim = function() {
    return this.replace(/(\s*$)/g, "");
};

/**
 * Remove all html tag
 *
 * @param str
 * @returns
 */
function delHtmlTag(str) {
    return str.replace(/<[^>]+>/g, "");
}

/**
 * judge whether the value is null
 *
 *  the value should not be 'null' and '0' and not the instance of boolean
 * @param val
 * @returns {boolean}
 */
function isNull(val) {
    return !val && val !== 0 && typeof val !== "boolean";
}

/**
 * return data satisfied query options
 * @param {object} query - the query options to filter out data
 * @param {object} result - data which to be filtered
 */
function queryFunc(query, result) {
    return result.filter(function(element) {
        var flag = true;

        Object.keys(query).forEach(function(key) {
            //if not satisfied anyone option of the query, set flag to false
            if (Array.isArray(query[key])) {
                //if query value is array
                //if flag is false, the element is not satisfied return false
                //if flag is true, judge whether the element's corresponding property value is contained in the array
                flag = !flag ? flag : query[key].indexOf(element[key]) !== -1;
            } else {
                //if flag is false, the element is not satisfied return false
                //if flag is true, judge whether the element's corresponding property value is equal to query value
                flag = !flag ? flag : query[key] == element[key];
            }
        });
        return flag;
    });
}

/**
 * return whether the type of args is string
 * @param data - data to get type
 */
function isString(data) {
    var flag = "";
    if (Array.isArray(data)) {
        flag = "array";
    } else if (typeof data == "object") {
        flag = "object";
    } else if (typeof data == "string") {
        flag = "string";
    }
    return flag == "string";
}

/**
 *show notification popup in ionic 
 *@param $scope
 *@param $ionicPopup
 *@param content
 *@param callback
 */

function showNotification($scope, $ionicPopup, T, content, callback) {
    var notifyPopup = $ionicPopup.show({
        template: content,
        title: T.T("Notification"),
        subTitle: '',
        scope: $scope,
        buttons: [{
            text: T.T("publicOK"),
            type: 'button-positive',
            onTap: function(e) {
                if (callback)
                    callback(e);
                notifyPopup.close();
            }
        }]
    });
}

/**
 * show alert popup in ionic
 * @param $scope
 * @param $ionicPopup
 * @param content
 * @param callback
 */
function showAlert($scope, $ionicPopup, T, content, callback) {
    var alertPopup = $ionicPopup.show({
        template: content,
        title: T.T("Alert"),
        subTitle: '',
        scope: $scope,
        buttons: [{
            text: T.T("publicOK"),
            type: 'deepBlue-bg light',
            onTap: function(e) {
                if (callback)
                    callback(e);
                alertPopup.close();
            }
        }]
    });
}

/**
 * show Confirm popup in ionic
 * @param $scope
 * @param $ionicPopup
 * @param content
 * @param callback
 */
function showConfirm($scope, $ionicPopup, T, content, callback) {
    var alertPopup = $ionicPopup.show({
        template: content,
        title: T.T("confirmation"),
        subTitle: '',
        scope: $scope,
        buttons: [{
            text: T.T("publicOK"),
            type: 'deepBlue-bg light',
            onTap: function(e) {
                if (callback)
                    callback(e);
                alertPopup.close();
            }
        }, {
            text: T.T("publicCancel"),
            type: 'button-default',
            onTap: function(e) {
                alertPopup.close();
            }
        }]
    });
}

function getLanguage(callback) {
    navigator.globalization.getPreferredLanguage(onSuccess, onError);

    function onSuccess(language) {
        if (callback) {
            callback.sunccess(language);
        }
    }

    function onError(err) {
        callback.failure(err);
    }

}