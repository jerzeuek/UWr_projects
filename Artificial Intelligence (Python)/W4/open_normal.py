import subprocess

# Path to the executable file
exe_path = "zad1_normal.exe"

# Run the executable without opening a new Command Prompt window
subprocess.Popen(exe_path, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE)