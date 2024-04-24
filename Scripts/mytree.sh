#!/bin/bash

# Function to recursively list directories and files
list_files() {
    local directory="$1"
    local indent="${2:-""}"
    local png_exclude="$3"

    # Loop through directory contents
    for file in "$directory"/*; do
        # Get file/directory name
        local name=$(basename "$file")

        # Check if it's a directory
        if [ -d "$file" ]; then
            echo "$indent├── $name/"
            # Recursively list directory contents
            list_files "$file" "$indent   " "$png_exclude"
        # Check if it's a regular file
        elif [ -f "$file" ]; then
            # Exclude PNG files
            if [ "$png_exclude" = "true" ] && [[ "$name" == *.png ]]; then
                continue
            fi
            echo "$indent├── $name"
        fi
    done
}

# Check if a directory is provided, otherwise use the current directory
if [ $# -eq 0 ]; then
    start_dir="."
else
    start_dir="$1"
fi

# Check if PNG exclusion is requested
if [ "$2" = "--exclude-png" ]; then
    png_exclude="true"
else
    png_exclude="false"
fi

# Main execution
echo "$(realpath "$start_dir")/"
list_files "$start_dir" "" "$png_exclude"


##./mytree.sh [directory] [--exclude-png]

## mytree.sh ../networkchess --exclude-png
