import turtle

turtle = turtle.Turtle()

turtle.speed(0)

for i in range(7):
    for color in ["cyan", "blue", "red", "yellow", "green"]:
        turtle.color(color)
        turtle.circle(100)
        turtle.left(10)


turtle.hideturtle()