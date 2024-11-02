# Specify the path to the Python script
$pythonScriptPath = "D:\uniwerek\SI\W4\zad1_random.exe"

# Check if the Python script exists
if (Test-Path $pythonScriptPath) {
    # Run the Python script
    python $pythonScriptPath
} else {
    Write-Host "The specified Python script does not exist."
}