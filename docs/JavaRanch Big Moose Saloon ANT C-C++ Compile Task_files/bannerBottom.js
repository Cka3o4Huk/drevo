function getBottomInclude()
{
    if (window.customOnTop != null) {
        if (customOnTop == 20) {
            document.write(idea);
        } else if (customOnTop == 19) {
            document.write(teamCity);
        } else if (customOnTop > 12) {
            document.write(wranglers0);
        } else {
            document.write(google);
        }
    }
}