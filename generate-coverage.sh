#!/bin/bash

# JaCoCo Code Coverage Generation Script for Unix/Linux/macOS
# This script generates code coverage reports for all modules

echo ""
echo "========================================"
echo "JaCoCo Code Coverage Generation"
echo "========================================"
echo ""

# Get the script's directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Navigate to backend directory
cd "$SCRIPT_DIR/backend" || exit 1

# Clean and run tests with coverage
echo "Running tests and generating coverage reports..."
mvn clean test jacoco:report -DskipTests=false

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Test execution failed!"
    echo ""
    exit 1
fi

echo ""
echo "========================================"
echo "Coverage Reports Generated Successfully"
echo "========================================"
echo ""
echo "Coverage reports are available at:"
echo ""

# Find all jacoco reports
for report in $(find . -path "*/target/site/jacoco/index.html" 2>/dev/null); do
    echo "  - $report"
done

echo ""
echo "To view coverage reports:"
echo "  1. Open the index.html files in your browser"
echo "  2. Or run: mvn jacoco:report && open ./MODULE/target/site/jacoco/index.html"
echo ""

# Return to original directory
cd "$SCRIPT_DIR" || exit 1
exit 0
