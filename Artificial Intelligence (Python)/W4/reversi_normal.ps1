# Specify the path to the executable file
$exePath = "D:\uniwerek\SI\W4\zad1_normal.exe"

# Check if the file exists
if (Test-Path $exePath) {
    # Run the executable
    Start-Process -FilePath $exePath
} else {
    Write-Host "The specified executable file does not exist."
}