double : Int -> Int
double x = x + x

identityInt : Int -> Int
identityInt x = x

identityString : String -> String
identityString x = x

identityBool : Bool -> Bool
identityBool x = x

identity : ty -> ty
identity x = x

-- quadrable : Int ->  Int
-- quadrable x = double double x

quadruple : Num a => a -> a
quadruple x = double (double x)

add : Int -> Int -> Int
add x y = x + y

Shape : Type
rotate : Shape -> Shape

