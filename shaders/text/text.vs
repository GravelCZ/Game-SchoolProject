#version 330 core

in vec2 pos;
in vec4 color;
in vec2 texcoord;

out vec4 vertexColor;
out vec2 textureCoord;

uniform float scale;
uniform mat4 projection;

void main()
{
	vertexColor = color;
	textureCoord = texcoord;
	
	gl_Position = projection * vec4(scale, scale, scale, 1.0) * vec4(pos, 0.0, 1.0);
}