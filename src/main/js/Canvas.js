function CoOrdinateException(message) {
    this.message = message;
    this.name = 'UserException';
}

class Canvas {
    constructor(width, height) {
        this.width = width;
        this.height = height;
        this.pixels = Array(height).fill(0).map(x => Array(width).fill(0))
    }

    assign(x, y, char) {
        this.pixels[y - 1][x - 1] = char
    }

    get(x, y) {
        return this.pixels[y - 1][x - 1] + ''
    }

    drawVLine(x1, y1, x2, y2) {
        if (y2 < y1) throw Error("Y2 should be same or higher")
        if (x1 !== x2) throw Error("X should be same")
        let length = y2 - y1
        for (var y = 0; y <= length; y++) {
            this.assign(x1, y1 + y, 'x')
        }
    }

    drawHLine(x1, y1, x2, y2) {
        if (x2 < x1) throw Error("X2 should be same or higher")
        if (y1 !== y2) throw Error("Y should be same")
        let length = x2 - x1
        for (var x = 0; x <= length; x++) {
            this.assign(x1 + x, y1, 'x')
        }
    }

    drawRectangle(x1, y1, x2, y2) {
        this.drawHLine(x1, y1, x2, y1)
        this.drawHLine(x1, y2, x2, y2)
        this.drawVLine(x1, y1, x1, y2)
        this.drawVLine(x2, y1, x2, y2)
    }


    isFill(x1, y1) {
        return this.get(x1, y1) !== "0"
    }

    fill(x, y, char, visited) {
        let key = x + ',' + y
        if (visited[key] !== key) {
            let validCordinate = x <= this.width && y <= this.height && x >= 1 && y >= 1
            if (!validCordinate) return 0;
            if (this.isFill(x, y)) {
                return 0;
            } else {
                visited[key] = key
                this.assign(x, y, char,visited)
                this.fill(x, y - 1, char,visited)
                this.fill(x + 1, y + 1, char,visited)
                this.fill(x + 1, y - 1, char,visited)
                this.fill(x + 1, y, char,visited)
                this.fill(x - 1, y + 1, char,visited)
                this.fill(x - 1, y - 1, char,visited)
                this.fill(x - 1, y, char,visited)
                this.fill(x, y + 1, char,visited)
            }
        }
        return 0
    }

    print() {
        for (var y = 1; y <= this.height; y++) {
            for (var x = 1; x <= this.width; x++) {
                process.stdout.write(this.get(x, y))
            }
            console.log("")
        }
    }

}

var canvas = new Canvas(20, 4)
canvas.drawHLine(1, 2, 6, 2)
canvas.drawVLine(6, 3, 6, 4)
canvas.drawRectangle(14, 1, 18, 3)
canvas.fill(10, 3, 'o', {})
canvas.print()