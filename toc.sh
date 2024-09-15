#!/bin/bash

# Output markdown file
output_file="TOC.md"
# Clear the output file
echo "" > $output_file

# Function to check if a directory contains any markdown files
contains_markdown() {
    local dir_path=$1
    # Use find to look for markdown files only
    find "$dir_path" -maxdepth 1 -type f -name "*.md" | grep -q .
}

# Function to generate bullet points for each directory containing markdown files
generate_toc() {
    local dir_path=$1
    local indent=$2

    # Check if the directory contains any markdown files
    if contains_markdown "$dir_path"; then
        echo "${indent}- **${dir_path}**" >> $output_file

        # List markdown files in the current directory
        find "$dir_path" -maxdepth 1 -type f -name "*.md" | while read file; do
            file_name=$(basename "$file")
            echo "${indent}  - [$file_name]($file)" >> $output_file
        done
    fi

    # Recursively process subdirectories, ignoring the .git directory
    find "$dir_path" -mindepth 1 -type d -not -path '*/.git/*' | while read subdir; do
        if contains_markdown "$subdir"; then
            generate_toc "$subdir" "$indent  "
        fi
    done
}

# Check if the input directory is provided as a parameter
if [ -z "$1" ]; then
    # Default to current directory if no argument is provided
    root_dir="."
else
    root_dir="$1"
fi

# Check if the directory exists
if [ ! -d "$root_dir" ]; then
    echo "Error: Directory $root_dir does not exist."
    exit 1
fi

# Start generating the TOC
generate_toc "$root_dir" ""

echo "TOC generated in $output_file"
