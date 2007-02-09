// Value returned is between start and end, inclusive
function GetRandom(start,end)
{
  var range = end - start + 1;
  result = start + Math.floor(Math.random()*range);
  return result;
}

var result=0;
google_ad_client = "pub-4768842087373098";
google_ad_width = 468;
google_ad_height = 60;
google_ad_format = "468x60_as";
google_ad_type = "text";
google_ad_channel ="";
google_color_border = "336699";
google_color_bg = "FFFFFF";
 google_color_link = "0000FF";
google_color_url = "008000";
google_color_text = "000000";

// Google
var google = "<center><script type='text/javascript' src='http://pagead2.googlesyndication.com/pagead/show_ads.js'></script></center>";


// Examulator
var examulator = "<a href=http://www.javaranch.com/clickServlet?href=http://www.examulator.com/tamer/course.htm   target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/examulator.gif' width='492' height='84' alt='Examulator' border=0></a>";

// Book promotion
var book = "This week's book giveaway is in the <a href='http://saloon.javaranch.com/cgi-bin/ubb/ultimatebb.cgi?ubb=forum&f=14'>Other Java Products and Servers</a> forum.<br>We're giving away four copies of <i>Alfresco Enterprise Content Management Implementation</i> and have Munwar Shariff on-line!<br>See <a href='http://saloon.javaranch.com/cgi-bin/ubb/ultimatebb.cgi?ubb=get_topic&f=14&t=001558'>this thread</a> for details.";

// IDEA
var idea = "<a href=http://www.javaranch.com/clickServlet?href=http://www.jetbrains.com/idea?6jr target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/idea60_468x60.gif' width='468' height='60' alt='IntelliJ IDEA 6.0' border=0></a>";

// TEAM CITY
var teamCity = "<a href=http://www.javaranch.com/clickServlet?href=http://www.jetbrains.com/teamcity?tc1jr target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/tc12_480x60.gif' width='480' height='60' alt='Team City 1.2' border=0></a>";

// Wranglers
var wranglers0 = "<a href=http://www.javaranch.com/clickServlet?href=http://www.codewranglers.com target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/wrangler_banner_1y.gif'   width=492 height=84 alt='CodeWranglers' border=0></a>";
var wranglers1 = "<a href=http://www.javaranch.com/clickServlet?href=http://www.codewranglers.com target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/19K_roll_resume.gif'   width=492 height=84 alt='CodeWranglers' border=0></a>";
var wranglers2 = "<a href=http://www.javaranch.com/clickServlet?href=http://www.codewranglers.com target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/roi17K_nofade.gif'   width=492 height=84 alt='CodeWranglers' border=0></a>";
var wranglers3 = "<a href=http://www.javaranch.com/clickServlet?href=http://www.codewranglers.com target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/wrangler_banner_rolling_resumes.gif'   width=492 height=84 alt='CodeWranglers' border=0></a>";

// ThinkGeek
var geek = '<a href="http://www.dpbolvw.net/click-2239372-10356343" target="_blank"><img src="http://www.tqlkg.com/image-2239372-10356343" width="468" height="60" alt="ThinkGeek T-Shirts will make you cool!" border="0"/></a>';

// ProjectCards
var pcards = "<a href=http://www.javaranch.com/clickServlet?href=http://www.projectcards.com/jr target=_blank><img src='http://www.javaranch.com/imageServlet?img=http://www.javaranch.com/banners/images/pcards-javaranch-468x60.gif' width='468' height='60' alt='ProjectCards' border=0></a>";
 
function announcements() {
     document.write('<center><table width=70% vspace=30 border=1 cellpadding=15><tr bgcolor=#f0e6d5><td><center><font size="2">');

     document.write(book);

     document.write("</font></center></td></tr></table></center>");
}

var customOnTop = GetRandom(1,20);

function getInclude()
{
   if (customOnTop == 1) {
        document.write(idea);  
   } else if (customOnTop == 2) {
        document.write(teamCity); 
   } else if (customOnTop < 9) {
        document.write(wranglers0); 
   } else if (customOnTop < 13) {
        document.write(pcards); 
   } else if (customOnTop < 16) {
       announcements();
   } else {
        document.write(google);
   } 
}

