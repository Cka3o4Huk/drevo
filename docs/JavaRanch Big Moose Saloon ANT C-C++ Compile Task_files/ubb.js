// --------------------------------------------------------------------------------------


String.prototype.toLocaleDate = function(){
  var date = parseFormattedJavaRanchDateString(this.toString());
  var localDate = date.toLocaleDateString();
  var date2 = new Date();
  var localToday = date2.toLocaleDateString();
  date2.setTime(date2.getTime() - 24*3600000);
  var localYesterday = date2.toLocaleDateString();
  if (localDate == localToday) {
    localDate = "Today";
  } else if (localDate == localYesterday) {
    localDate = "Yesterday";
  }
  document.write(localDate);
}

// --------------------------------------------------------------------------------------

String.prototype.toLocaleTime = function(){
  var date = parseFormattedJavaRanchDateString(this.toString());
  var localDate = date.toLocaleTimeString().replace(/(\d\d?\:\d\d)\:00/,"$1");
  document.write(localDate);
}

// --------------------------------------------------------------------------------------

// for a given element, get the date with timezone
function parseFormattedJavaRanchDateString(dateTime) {
  var date = new Date (dateTime);
  var coloradoOffsetInHours = calculateColoradoOffsetFromGmt();
  date.setHours(date.getHours() + coloradoOffsetInHours);
  return date;
}

// --------------------------------------------------------------------------------------

/*
 * use current (2007) daylight savings time rules
 * 
 * Known limitations:
 * 1) Older posts will have their time translation off by an hour for 
 *    the weeks between the old and new days for changing clocks.  This function uses
 *    2007 clock change logic.
 * 2) The time of posts is off by an hour on the day we change the clocks in the US
 *    around 2 am Colorado time.
 * 
 * Colorado is GMT/UTC - 7:00 during the winter and GMT - 6:00 hours in the summer
 */
function calculateColoradoOffsetFromGmt() {
  var today = new Date();

  // get user's timezone - for example, this returns for 5 for NY and -1 for Belgium
  var userTimeZoneOffset = today.getTimezoneOffset() / 60;

  // assume Colorado is 7 hours from GMT (this introduces an error within an hour of
  // changing the clocks on clock change day)
  // we need this logic to avoid being wrong for half a day for users in Australlia
  var extraColoradoTimeZoneOffset = 7 - userTimeZoneOffset;

  // subtract extra Colorado offset to have Colorado day/time within an hour
  // this is closer to being correct than using the user's timezone for determining
  // if it is the day to switch the clocks  
  today.setHours(today.getHours() - 2);

  // check if daylight savings time and return correct offset
  // the call getTimezoneOffset() doesn't accomodate daylight saving's time
  if (isDaylightSavingsTimeInColorado(today)) {
    return extraColoradoTimeZoneOffset - 1;
  } else {
    return extraColoradoTimeZoneOffset;
  }
}

// --------------------------------------------------------------------------------------

/*
 * Use 2007 daylight savings time rules:
 * http://wwp.greenwichmeantime.com/time-zone/rules/usa.htm
 */
function isDaylightSavingsTimeInColorado(currentColoradoDate) {

  // daylight savings time from second sunday in March to first Sunday in November
  var startOfDaylightSavings = createDateForSundayAt2am(2, 2);
  var endOfDaylightSavings = createDateForSundayAt2am(1, 10);
  return startOfDaylightSavings < currentColoradoDate
    && endOfDaylightSavings > currentColoradoDate;
}

// --------------------------------------------------------------------------------------

/*
 * return date for the given sunday number (1-4) in the given month (0-11) for the current year
 * 
 * Known limitations:
 * 1) Uses date for existing year - this may be off on December 31st and January 1st for
 *    international users. This is ok because the clocks don't ever change that day.
 * 
 * Colorado is GMT/UTC - 7:00 during the winter and GMT - 6:00 hours in the summer
 */
function createDateForSundayAt2am(sundayNumber, monthNumber) {

  var date = new Date();
  date.setMonth(monthNumber);
  date.setDate(1);
  date.setHours(1);
  date.setMinutes(0);
  date.setSeconds(0);
  date.setMilliseconds(0);

  var numberSundaysPassed = 0;
  while ( numberSundaysPassed < sundayNumber ) {
    date.setDate(date.getDate() + 1);
    // if sunday
    if ( date.getDay() == 0 ) {
      numberSundaysPassed++;
    }
  }
  return date;
}

// --------------------------------------------------------------------------------------

function warnIfOldThread() {
  if (isOldThread) { 
    alert('The posts in this thread are old, and it is unlikely that the original posters are still interested in responses.  Are you sure you still wish to reply to this thread?');
  }
}
